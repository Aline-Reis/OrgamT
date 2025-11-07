package br.edu.ifto.OrgTask.model;

import jakarta.persistence.*;
import java.time.*;

@Entity
public class Tarefa {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Usuario dono;

    @Column(nullable = false)
    private String titulo;

    @Column(length = 4000)
    private String descricao;

    private LocalDate prazo;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade = Prioridade.MEDIA;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ABERTA;

    private Instant criadoEm;
    private Instant atualizadoEm;

    @PrePersist
    public void prePersist(){
        this.criadoEm = Instant.now();
        this.atualizadoEm = this.criadoEm;
    }
    @PreUpdate
    public void preUpdate(){
        this.atualizadoEm = Instant.now();
    }

    // Getters/Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getDono() { return dono; }
    public void setDono(Usuario dono) { this.dono = dono; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDate getPrazo() { return prazo; }
    public void setPrazo(LocalDate prazo) { this.prazo = prazo; }
    public Prioridade getPrioridade() { return prioridade; }
    public void setPrioridade(Prioridade prioridade) { this.prioridade = prioridade; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public Instant getCriadoEm() { return criadoEm; }
    public Instant getAtualizadoEm() { return atualizadoEm; }
}