package br.edu.ifto.OrgTask.controller;

import br.edu.ifto.OrgTask.model.*;
import br.edu.ifto.OrgTask.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tarefas")
public class TarefaController {
    private final TarefaRepository tarefas;
    private final UsuarioRepository usuarios;

    public TarefaController(TarefaRepository tarefas, UsuarioRepository usuarios){
        this.tarefas = tarefas; this.usuarios = usuarios;
    }

    private Usuario current(Authentication auth){
        return usuarios.findByEmailCpf(auth.getName()).orElseThrow();
    }

    @GetMapping
    public String listar(Authentication auth, Model model){
        Usuario u = current(auth);
        model.addAttribute("usuario", u);
        model.addAttribute("tarefas", tarefas.findByDonoIdOrderByPrazoAsc(u.getId()));
        model.addAttribute("prioridades", Prioridade.values());
        model.addAttribute("statuses", Status.values());
        model.addAttribute("nova", new Tarefa());
        return "tarefas";
    }

    @PostMapping
    public String criar(@ModelAttribute("nova") Tarefa t, Authentication auth){
        t.setDono(current(auth));
        tarefas.save(t);
        return "redirect:/tarefas";
    }

    @PostMapping("/{id}/status")
    public String status(@PathVariable Long id, @RequestParam Status s){
        Tarefa t = tarefas.findById(id).orElseThrow();
        t.setStatus(s);
        tarefas.save(t);
        return "redirect:/tarefas";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        tarefas.deleteById(id);
        return "redirect:/tarefas";
    }
}