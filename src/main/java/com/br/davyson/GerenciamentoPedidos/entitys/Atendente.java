package com.br.davyson.GerenciamentoPedidos.entitys;

import com.br.davyson.GerenciamentoPedidos.dto.AtendenteRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Atendente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Login do usuário é obrigatório")
    private String login;

    @NotBlank(message = "Nome do usuário é obrigatório")
    private String nome;

    @NotBlank(message = "O usuário deve ter uma senha")
    private String senha;

    @OneToMany(mappedBy = "atendente", fetch = FetchType.LAZY)
    private List<Pedido> pedidos = new ArrayList<>();

    public Atendente(String login, String nome, String senha) {
        this.login = login;
        this.nome = nome;
        this.senha = senha;
    }

    public Atendente(AtendenteRequestDTO dto) {
        this.nome = dto.nome();
        this.login = dto.login();
        this.senha = dto.senha();
    }

    public Atendente(){}


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Atendente atendente = (Atendente) o;
        return Objects.equals(id, atendente.id) && Objects.equals(login, atendente.login) && Objects.equals(nome, atendente.nome) && Objects.equals(senha, atendente.senha) && Objects.equals(pedidos, atendente.pedidos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, nome, senha, pedidos);
    }
}
