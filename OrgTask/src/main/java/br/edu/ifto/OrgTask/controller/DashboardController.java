package br.edu.ifto.OrgTask.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/meu-dia")
    public String meuDia(Model model, Authentication auth) {
        String display = (auth != null && auth.isAuthenticated()) ? auth.getName() : "Usuário";
        model.addAttribute("nome", display);
        return "meu-dia";
    }
}
