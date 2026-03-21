package com.br.davyson.GerenciamentoPedidos.dto;

import com.br.davyson.GerenciamentoPedidos.entitys.Pedido;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO (Long id, Integer mesa, String nomeAtendente, List<String>comidas,
                                 String observacao, BigDecimal subTotal, BigDecimal taxaServico, BigDecimal valorTotal,
                                    BigDecimal valorPago, BigDecimal saldoRestante,
                                 @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime data){

    public PedidoResponseDTO(Pedido pedido){
        this(
                pedido.getId(),
                pedido.getMesa(),
                pedido.getAtendente()!= null ? pedido.getAtendente().getNome() : "Não atribuído",
                pedido.getComidas().stream()
                        .map(comida -> comida.getNome() + " - R$ " + comida.getPreco())
                        .toList(),
                pedido.getObservacao(),
                pedido.getSubtotal(),
                pedido.getTaxaServico(),
                pedido.getValorTotal(),
                pedido.getValorPago(),
                pedido.getSaldoRestante(),
                pedido.getData()
        );
    }
}
