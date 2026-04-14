package com.br.davyson.GerenciamentoPedidos.entitys;

import com.br.davyson.GerenciamentoPedidos.dto.request.AtendenteRegisterRequest;
import com.br.davyson.GerenciamentoPedidos.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "atendente")
public class Atendente implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "E-mail do usuário é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    private String email;

    @NotBlank(message = "Nome do usuário é obrigatório")
    private String nome;

    @NotBlank(message = "O usuário deve ter uma senha")
    private String senha;

    @OneToMany(mappedBy = "atendente", fetch = FetchType.LAZY)
    private List<Pedido> pedidos = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    public Atendente(String login, String nome, String senha) {
        this.email = login;
        this.nome = nome;
        this.senha = senha;
    }

    public Atendente(AtendenteRegisterRequest dto) {
        this.nome = dto.nome();
        this.email = dto.email();
        this.senha = dto.senha();
    }

    public Atendente(){}


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Atendente atendente = (Atendente) o;
        return Objects.equals(id, atendente.id) && Objects.equals(email, atendente.email) && Objects.equals(nome, atendente.nome) && Objects.equals(senha, atendente.senha) && Objects.equals(pedidos, atendente.pedidos) && role == atendente.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, nome, senha, pedidos, role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
