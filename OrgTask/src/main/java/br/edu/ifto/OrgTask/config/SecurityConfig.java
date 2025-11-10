package br.edu.ifto.OrgTask.config;

import br.edu.ifto.OrgTask.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioRepository repo){
        return username -> repo.findByEmailCpf(username)
                .map(u -> User.withUsername(u.getEmailCpf())
                        .password(u.getSenhaHash())
                        .roles("USER")
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // permitir recursos estáticos, páginas públicas e a página inicial ("/")
                        .requestMatchers("/","/css/**","/js/**","/images/**", "/login","/cadastro","/signup","/h2/**").permitAll()
                        .anyRequest().authenticated())
                .headers(h -> h.frameOptions(f -> f.disable())) // permite H2 console
                .formLogin(f -> f
                        .loginPage("/login")
                        .defaultSuccessUrl("/tarefas", true)
                        .permitAll())
                .logout(l -> l
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());
        return http.build();
    }
}