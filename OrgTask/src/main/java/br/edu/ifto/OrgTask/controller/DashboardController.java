package br.edu.ifto.OrgTask.controller;

import br.edu.ifto.OrgTask.model.Tarefa;
import br.edu.ifto.OrgTask.model.Usuario;
import br.edu.ifto.OrgTask.repository.TarefaRepository;
import br.edu.ifto.OrgTask.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class DashboardController {

    private final UsuarioRepository usuarios;
    private final TarefaRepository tarefas;

    public DashboardController(UsuarioRepository usuarios,
                               TarefaRepository tarefas) {
        this.usuarios = usuarios;
        this.tarefas = tarefas;
    }

    @GetMapping("/meu-dia")
    public String meuDia(Model model, Authentication auth) {
        String username = (auth != null && auth.isAuthenticated())
                ? auth.getName()
                : null;

        Usuario u = null;
        if (username != null) {
            u = usuarios.findByEmailCpf(username).orElse(null);
        }

        // Nome exibido no topo
        String nomeExibicao = (u != null ? u.getNome() : (username != null ? username : "Usuário"));
        model.addAttribute("nome", nomeExibicao);

        // Data de hoje
        LocalDate hoje = LocalDate.now();
        model.addAttribute("hoje", hoje);

        // Tarefas marcadas para hoje
        List<Tarefa> tarefasHoje;
        if (u != null) {
            tarefasHoje = tarefas.findByDonoIdAndPrazoOrderByPrazoAsc(u.getId(), hoje);
        } else {
            tarefasHoje = List.of();
        }
        model.addAttribute("tarefasHoje", tarefasHoje);

        return "meu-dia";
    }
}
