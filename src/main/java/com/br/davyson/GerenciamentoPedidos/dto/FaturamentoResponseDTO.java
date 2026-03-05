package com.br.davyson.GerenciamentoPedidos.dto;

import java.math.BigDecimal;

public record FaturamentoResponseDTO(BigDecimal totalSemana, BigDecimal totalQuinzena, BigDecimal totalMensal) {

}
