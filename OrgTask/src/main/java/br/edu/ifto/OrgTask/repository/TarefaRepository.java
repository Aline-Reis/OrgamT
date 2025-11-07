package br.edu.ifto.OrgTask.repository;


import br.edu.ifto.OrgTask.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByDonoIdOrderByPrazoAsc(Long donoId);
}