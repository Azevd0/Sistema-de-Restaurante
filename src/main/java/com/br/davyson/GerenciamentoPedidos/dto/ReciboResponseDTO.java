package com.br.davyson.GerenciamentoPedidos.dto;

import com.br.davyson.GerenciamentoPedidos.entitys.Recibo;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
public record ReciboResponseDTO (Long id, @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime dataDeFechamento,
                                 BigDecimal valorTotal, String formaDePagamento, String bandeiraCartao, Integer qtdDePessoas,
                                 BigDecimal mediaPorPessoa){

    public ReciboResponseDTO(Recibo recibo){
        this(
                recibo.getId(),
                recibo.getDataFechamento(),
                recibo.getValorTotal(),
                recibo.getFormaPagamento() != null ? recibo.getFormaPagamento().name() : null,
                recibo.getBandeiraCartao() != null ? recibo.getBandeiraCartao().name() :null,
                recibo.getQtdDePessoas(),
                recibo.getMedia()
        );
    }
}
