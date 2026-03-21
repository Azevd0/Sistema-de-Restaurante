package com.br.davyson.GerenciamentoPedidos.entitys;

import com.br.davyson.GerenciamentoPedidos.enums.BandeiraCartao;
import com.br.davyson.GerenciamentoPedidos.enums.FormaPagamento;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Recibo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataFechamento = LocalDateTime.now();
    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;
    @Enumerated(EnumType.STRING)
    private BandeiraCartao bandeiraCartao;
    private Integer qtdDePessoas;
    private BigDecimal media;

    public Recibo(Long id, LocalDateTime dataFechamento, BigDecimal valorTotal, FormaPagamento formaPagamento, BandeiraCartao bandeiraCartao, Integer qtdDePessoas, BigDecimal media) {
        this.id = id;
        this.dataFechamento = dataFechamento;
        this.valorTotal = valorTotal;
        this.formaPagamento = formaPagamento;
        this.bandeiraCartao = bandeiraCartao;
        this.qtdDePessoas = qtdDePessoas;
        this.media = media;
    }

    public Recibo(){}

    public Recibo(Pedido pedido, FormaPagamento formaPagamento) {
        this.dataFechamento = pedido.getData();
        this.valorTotal = pedido.getValorPago();
        this.formaPagamento = formaPagamento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(LocalDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public BandeiraCartao getBandeiraCartao() {
        return bandeiraCartao;
    }

    public void setBandeiraCartao(BandeiraCartao bandeiraCartao) {
        this.bandeiraCartao = bandeiraCartao;
    }

    public Integer getQtdDePessoas() {
        return qtdDePessoas;
    }

    public void setQtdDePessoas(Integer qtdDePessoas) {
        this.qtdDePessoas = qtdDePessoas;
    }

    public BigDecimal getMedia() {return media;}

    public void setMedia(BigDecimal media) {this.media = media;}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Recibo recibo = (Recibo) o;
        return Objects.equals(id, recibo.id) && Objects.equals(dataFechamento, recibo.dataFechamento) && Objects.equals(valorTotal, recibo.valorTotal) && formaPagamento == recibo.formaPagamento;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataFechamento, valorTotal, formaPagamento);
    }
}
