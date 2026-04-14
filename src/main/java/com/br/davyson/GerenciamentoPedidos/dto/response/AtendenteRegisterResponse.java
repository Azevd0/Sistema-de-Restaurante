package com.br.davyson.GerenciamentoPedidos.dto.response;

import com.br.davyson.GerenciamentoPedidos.entitys.Atendente;

public class AtendenteRegisterResponse {
    private Long id;
    private String nome;
    private String email;
    private String cargo;

    public AtendenteRegisterResponse() {}

    public AtendenteRegisterResponse(Atendente atendente) {
        this.id = atendente.getId();
        this.nome = atendente.getNome();
        this.email = atendente.getEmail();
        this.cargo = atendente.getRole() != null ? atendente.getRole().name() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
