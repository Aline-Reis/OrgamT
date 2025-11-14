package br.edu.ifto.OrgTask.controller;

import br.edu.ifto.OrgTask.model.*;
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

    private Usuario atual(Authentication auth) {
        return usuarios.findByEmailCpf(auth.getName()).orElseThrow();
    }

    @GetMapping("/tarefas")
    public String listar(Model model, Authentication auth,
                         @RequestParam(value = "nova", required = false) String ignora) {
        Usuario u = atual(auth);
        model.addAttribute("usuario", u);
        model.addAttribute("tarefas", tarefas.findByDonoIdOrderByPrazoAsc(u.getId()));
        model.addAttribute("prioridades", Prioridade.values());
        model.addAttribute("statuses", Status.values());
        return "tarefas";
    }

    @PostMapping("/tarefas")
    public String criar(@RequestParam String titulo,
                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate prazo,
                        @RequestParam(required = false) Prioridade prioridade,
                        @RequestParam(required = false) Status status,
                        @RequestParam(required = false) String descricao,
                        Authentication auth) {
        Usuario u = atual(auth);
        Tarefa t = new Tarefa();
        t.setDono(u);
        t.setTitulo(titulo);
        t.setDescricao(descricao);
        if (prazo != null) t.setPrazo(prazo);
        if (prioridade != null) t.setPrioridade(prioridade);
        if (status != null) t.setStatus(status);
        tarefas.save(t);
        return "redirect:/tarefas";
    }

    @PostMapping("/tarefas/{id}/status")
    public String mudarStatus(@PathVariable Long id, @RequestParam("s") Status s) {
        tarefas.findById(id).ifPresent(t -> { t.setStatus(s); tarefas.save(t); });
        return "redirect:/tarefas";
    }

    @PostMapping("/tarefas/{id}/delete")
    public String excluir(@PathVariable Long id) {
        tarefas.deleteById(id);
        return "redirect:/tarefas";
    }
}
