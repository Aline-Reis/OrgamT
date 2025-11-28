package br.edu.ifto.OrgTask.repository;


import br.edu.ifto.OrgTask.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByDonoIdOrderByPrazoAsc(Long donoId);

    @Query("SELECT t FROM Tarefa t WHERE t.dono.id = :donoId " +
            "AND t.prazo <= :hoje " +
            "AND t.status <> 'CONCLUIDA' " +
            "ORDER BY t.prazo ASC")
    List<Tarefa> findTarefasPendentesAteHoje(@Param("donoId") Long donoId,
                                             @Param("hoje") LocalDate hoje);
}