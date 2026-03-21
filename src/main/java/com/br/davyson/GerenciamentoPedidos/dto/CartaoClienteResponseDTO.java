package com.br.davyson.GerenciamentoPedidos.dto;

import com.br.davyson.GerenciamentoPedidos.entitys.CartaoCliente;

import java.math.BigDecimal;

public record CartaoClienteResponseDTO(Long id, String bandeiraCartao, String modalidade, BigDecimal saldo){

    public CartaoClienteResponseDTO(CartaoCliente cartaoCliente){
        this(
                cartaoCliente.getId(),
                cartaoCliente.getBandeiraCartao() != null ? cartaoCliente.getBandeiraCartao().name(): null,
                cartaoCliente.getFormaPagamento() != null ? cartaoCliente.getFormaPagamento().name() :null,
                cartaoCliente.getSaldo()
        );
    }
}
