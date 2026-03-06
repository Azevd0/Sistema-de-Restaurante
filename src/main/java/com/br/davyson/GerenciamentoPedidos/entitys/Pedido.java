package com.br.davyson.GerenciamentoPedidos.entitys;

import com.br.davyson.GerenciamentoPedidos.dto.PedidoRequestDTO;
import com.br.davyson.GerenciamentoPedidos.enums.FormaPagamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A mesa deve ter um número.")
    private Integer mesa;

    @ManyToOne
    @JoinColumn(name = "atendente_id")
    private Atendente atendente;

    @Version
    private Integer versao;

    @ManyToMany
    @JoinTable(name = "pedido_comidas", joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "comida_id"))
    private List<Comida> comidas = new ArrayList<>();
    private String observacao;
    private BigDecimal valorPago = BigDecimal.ZERO;
    private boolean statusPagamento;
    @Column(nullable = false, updatable = false)
    private LocalDateTime data = LocalDateTime.now();



    @Transient
    public BigDecimal getSubtotal() {
        if (comidas == null || comidas.isEmpty()) return BigDecimal.ZERO;
        return comidas.stream().map(Comida::getPreco).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    @Transient
    public BigDecimal getTaxaServico() {
        return getSubtotal().multiply(new BigDecimal("0.10")).setScale(2, RoundingMode.HALF_UP);
    }
    @Transient
    public BigDecimal getValorTotal() {
        return getSubtotal().add(getTaxaServico());
    }

    @Transient
    public BigDecimal getSaldoRestante() {
        return getValorTotal().subtract(valorPago).max(BigDecimal.ZERO);
    }

    public Pedido(){}
    public Pedido(PedidoRequestDTO dto, Atendente atendente) {
        this.mesa = dto.numeroMesa();
        this.atendente = atendente;
        this.observacao = dto.observacao();
        this.statusPagamento = false;
        this.comidas = new ArrayList<>();
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getVersao() {
        return versao;
    }

    public void setVersao(Integer versao) {
        this.versao = versao;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getMesa() { return mesa; }
    public void setMesa(Integer mesa) { this.mesa = mesa; }

    public Atendente getAtendente() { return atendente; }
    public void setAtendente(Atendente atendente) { this.atendente = atendente; }

    public List<Comida> getComidas() { return comidas; }
    public void setComidas(List<Comida> comidas) { this.comidas = comidas; }

    public boolean getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(boolean statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public LocalDateTime getData() {
        return this.data;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }
    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return statusPagamento == pedido.statusPagamento && Objects.equals(id, pedido.id) && Objects.equals(mesa, pedido.mesa) && Objects.equals(atendente, pedido.atendente) && Objects.equals(versao, pedido.versao) && Objects.equals(comidas, pedido.comidas) && Objects.equals(observacao, pedido.observacao) && Objects.equals(valorPago, pedido.valorPago) && Objects.equals(data, pedido.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mesa, atendente, versao, comidas, observacao, valorPago, statusPagamento, data);
    }
}


