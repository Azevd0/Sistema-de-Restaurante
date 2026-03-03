package com.br.davyson.GerenciamentoPedidos.dto;

import com.br.davyson.GerenciamentoPedidos.entitys.Pedido;
import com.br.davyson.GerenciamentoPedidos.entitys.Recibo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
public class ReciboResponseDTO {
    private Long id;
    private LocalDateTime dataFechamento;
    private BigDecimal valorTotal;
    private String formaPagamento;
    private Integer quantidadeDePessoas;
    private BigDecimal mediaPorPessoa;

    public ReciboResponseDTO(Recibo recibo) {
        this.id = recibo.getId();
        this.dataFechamento = recibo.getDataFechamento();
        this.valorTotal = recibo.getValorTotal();
        this.formaPagamento = (recibo.getFormaPagamento() != null) ? recibo.getFormaPagamento().name() : null;
        this.mediaPorPessoa = recibo.getMedia();
        this.quantidadeDePessoas = recibo.getQtdDePessoas();
    }

    public Long getId() { return id; }
    public LocalDateTime getDataFechamento() { return dataFechamento; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public String getFormaPagamento() { return formaPagamento; }
    public BigDecimal getMediaPorPessoa() {return mediaPorPessoa;}

    public Integer getQuantidadeDePessoas() {
        return quantidadeDePessoas;
    }

    public void setId(Long id) { this.id = id; }
    public void setDataFechamento(LocalDateTime dataFechamento) { this.dataFechamento = dataFechamento; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
    public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }
    public void setMediaPorPessoa(BigDecimal mediaPorPessoa) {this.mediaPorPessoa = mediaPorPessoa;}

    public void setQuantidadeDePessoas(Integer quantidadeDePessoas) {
        this.quantidadeDePessoas = quantidadeDePessoas;
    }
}
