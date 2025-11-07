package br.edu.ifto.OrgTask.model;

import jakarta.persistence.*;

@Entity
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true, nullable = false)
    private String emailCpf;


    @Column(nullable = false)
    private String senhaHash;


    @Column(nullable = false)
    private String nome;


    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmailCpf() { return emailCpf; }
    public void setEmailCpf(String emailCpf) { this.emailCpf = emailCpf; }
    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}