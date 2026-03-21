package com.br.davyson.GerenciamentoPedidos.entitys;

import com.br.davyson.GerenciamentoPedidos.enums.BandeiraCartao;
import com.br.davyson.GerenciamentoPedidos.enums.FormaPagamento;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cartao")
public class CartaoCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private BandeiraCartao bandeiraCartao;
    private FormaPagamento formaPagamento;
    private BigDecimal saldo;
    private String senha;

    public CartaoCliente(){}

    public CartaoCliente(Long id, BandeiraCartao bandeiraCartao, FormaPagamento formaPagamento, BigDecimal saldo, String senha) {
        this.id = id;
        this.bandeiraCartao = bandeiraCartao;
        this.formaPagamento = formaPagamento;
        this.saldo = saldo;
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BandeiraCartao getBandeiraCartao() {
        return bandeiraCartao;
    }

    public void setBandeiraCartao(BandeiraCartao bandeiraCartao) {
        this.bandeiraCartao = bandeiraCartao;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
