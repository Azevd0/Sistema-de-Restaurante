package com.br.davyson.GerenciamentoPedidos.dto;

import java.math.BigDecimal;

public record FaturamentoResponseDTO(BigDecimal hoje, BigDecimal totalSemana,
                                     BigDecimal totalQuinzena, BigDecimal totalMensal) {

}
