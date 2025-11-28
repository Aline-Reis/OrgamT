package br.edu.ifto.OrgTask.controller;

import br.edu.ifto.OrgTask.model.Prioridade;
import br.edu.ifto.OrgTask.model.Status;
import br.edu.ifto.OrgTask.model.Tarefa;
import br.edu.ifto.OrgTask.model.Usuario;
import br.edu.ifto.OrgTask.repository.TarefaRepository;
import br.edu.ifto.OrgTask.repository.UsuarioRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class TarefaController {

    private final TarefaRepository tarefas;
    private final UsuarioRepository usuarios;

    public TarefaController(TarefaRepository tarefas, UsuarioRepository usuarios) {
        this.tarefas = tarefas;
        this.usuarios = usuarios;
    }

    // pega o usuário logado a partir do e-mail/CPF (username)
    private Usuario atual(Authentication auth) {
        return usuarios.findByEmailCpf(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado na sessão"));
    }

    // LISTA DE TAREFAS (tarefas.html)
    @GetMapping("/tarefas")
    public String listar(Model model, Authentication auth,
                         @RequestParam(value = "nova", required = false) String ignora) {
        Usuario u = atual(auth);
        model.addAttribute("usuario", u);
        model.addAttribute("tarefas", tarefas.findByDonoIdOrderByPrazoAsc(u.getId()));
        model.addAttribute("prioridades", Prioridade.values());
        model.addAttribute("statuses", Status.values());
        return "tarefas"; // templates/tarefas.html
    }

    // FORMULÁRIO DE CRIAÇÃO (criar-tarefa.html)
    @GetMapping("/criar-tarefa")
    public String mostrarFormularioCriar(Model model, Authentication auth) {
        Usuario u = atual(auth);
        model.addAttribute("usuario", u);
        model.addAttribute("prioridades", Prioridade.values());
        // tarefas do dia
        LocalDate hoje = LocalDate.now();
        model.addAttribute("hoje", hoje);
        model.addAttribute("tarefasHoje",
                tarefas.findTarefasPendentesAteHoje(u.getId(), hoje));

        return "criar-tarefa";
    }

    // SALVAR NOVA TAREFA (POST /tarefas)
    @PostMapping("/tarefas")
    public String criar(Tarefa tarefa, Authentication auth) {
        Usuario u = atual(auth);
        tarefa.setDono(u);
        if (tarefa.getStatus() == null) tarefa.setStatus(Status.ABERTA);
        if (tarefa.getPrioridade() == null) tarefa.setPrioridade(Prioridade.MEDIA);
        tarefas.save(tarefa);
        return "redirect:/tarefas";
    }

    // MUDAR STATUS
    @PostMapping("/tarefas/{id}/status")
    public String mudarStatus(@PathVariable Long id, @RequestParam("s") Status s) {
        tarefas.findById(id).ifPresent(t -> {
            t.setStatus(s);
            tarefas.save(t);
        });
        return "redirect:/tarefas";
    }

    // EXCLUIR TAREFA
    @PostMapping("/tarefas/{id}/delete")
    public String excluir(@PathVariable Long id) {
        tarefas.deleteById(id);
        return "redirect:/tarefas";
    }
}
