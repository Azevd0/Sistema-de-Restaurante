package com.br.davyson.GerenciamentoPedidos.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "comida")
public class Comida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "A comida deve ter um nome.")
    private String nome;
    @NotBlank(message = "A comida deve ter uma descrição")
    private String descricao;

    @NotNull(message = "A comida deve ter um preço.")
    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    public Comida(){}

    public Comida(String nome, String descricao, BigDecimal preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Categoria getCategoria() {
        return categoria;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Comida comida = (Comida) o;
        return Objects.equals(id, comida.id) && Objects.equals(nome, comida.nome) && Objects.equals(descricao, comida.descricao) && Objects.equals(preco, comida.preco) && Objects.equals(categoria, comida.categoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, descricao, preco, categoria);
    }
}
