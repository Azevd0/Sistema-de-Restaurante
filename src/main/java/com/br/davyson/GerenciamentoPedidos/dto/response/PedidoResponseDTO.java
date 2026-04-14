package com.br.davyson.GerenciamentoPedidos.dto.response;

import com.br.davyson.GerenciamentoPedidos.entitys.Pedido;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoResponseDTO {

    private Long id;
    private Integer mesa;
    private String nomeAtendente;
    private List<String> comidas;
    private BigDecimal subTotal;
    private BigDecimal taxaServico;
    private BigDecimal valorTotal;
    private BigDecimal valorPago;
    private BigDecimal saldoRestante;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime data;

    public PedidoResponseDTO() {
    }

    public PedidoResponseDTO(Long id, Integer mesa, String nomeAtendente, List<String> comidas, BigDecimal subTotal, BigDecimal taxaServico,
                             BigDecimal valorTotal, BigDecimal valorPago, BigDecimal saldoRestante,
                             LocalDateTime data) {
        this.id = id;
        this.mesa = mesa;
        this.nomeAtendente = nomeAtendente;
        this.comidas = comidas;
        this.subTotal = subTotal;
        this.taxaServico = taxaServico;
        this.valorTotal = valorTotal;
        this.valorPago = valorPago;
        this.saldoRestante = saldoRestante;
        this.data = data;
    }

    public PedidoResponseDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.mesa = pedido.getMesa();
        this.nomeAtendente = pedido.getAtendente() != null ? pedido.getAtendente().getNome() : "Não atribuído";
        this.comidas = pedido.getComidas().stream()
                .map(comida -> comida.getNome() + " - R$ " + comida.getPreco())
                .toList();
        this.subTotal = pedido.getSubtotal();
        this.taxaServico = pedido.getTaxaServico();
        this.valorTotal = pedido.getValorTotal();
        this.valorPago = pedido.getValorPago();
        this.saldoRestante = pedido.getSaldoRestante();
        this.data = pedido.getData();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getMesa() { return mesa; }
    public void setMesa(Integer mesa) { this.mesa = mesa; }

    public String getNomeAtendente() { return nomeAtendente; }
    public void setNomeAtendente(String nomeAtendente) { this.nomeAtendente = nomeAtendente; }

    public List<String> getComidas() { return comidas; }
    public void setComidas(List<String> comidas) { this.comidas = comidas; }

    public BigDecimal getSubTotal() { return subTotal; }
    public void setSubTotal(BigDecimal subTotal) { this.subTotal = subTotal; }

    public BigDecimal getTaxaServico() { return taxaServico; }
    public void setTaxaServico(BigDecimal taxaServico) { this.taxaServico = taxaServico; }

    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    public BigDecimal getValorPago() { return valorPago; }
    public void setValorPago(BigDecimal valorPago) { this.valorPago = valorPago; }

    public BigDecimal getSaldoRestante() { return saldoRestante; }
    public void setSaldoRestante(BigDecimal saldoRestante) { this.saldoRestante = saldoRestante; }

    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }
}
