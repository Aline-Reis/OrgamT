package br.edu.ifto.OrgTask.repository;


import br.edu.ifto.OrgTask.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmailCpf(String emailCpf);
}