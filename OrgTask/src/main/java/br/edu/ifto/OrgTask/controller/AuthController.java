package br.edu.ifto.OrgTask.controller;


import br.edu.ifto.OrgTask.model.Usuario;
import br.edu.ifto.OrgTask.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class AuthController {
    private final UsuarioRepository repo;
    private final BCryptPasswordEncoder encoder;


    public AuthController(UsuarioRepository repo, BCryptPasswordEncoder encoder){
        this.repo = repo; this.encoder = encoder;
    }


    @GetMapping("/login")
    public String login(){ return "login"; }


    @GetMapping("/cadastro")
    public String cadastro(){ return "cadastro"; }


    @PostMapping("/signup")
    public String signup(@RequestParam String nome,
                         @RequestParam String emailCpf,
                         @RequestParam String senha,
                         RedirectAttributes ra){
        if(repo.findByEmailCpf(emailCpf).isPresent()){
            ra.addFlashAttribute("err","Usuário já existe");
            return "redirect:/cadastro";
        }
        Usuario u = new Usuario();
        u.setNome(nome);
        u.setEmailCpf(emailCpf);
        u.setSenhaHash(encoder.encode(senha));
        repo.save(u);
        ra.addFlashAttribute("ok","Conta criada! Faça login.");
        return "redirect:/login";
    }
}