package com.br.davyson.GerenciamentoPedidos.dto.response;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;

import java.math.BigDecimal;

@RegisterReflectionForBinding({ FaturamentoResponseDTO.class })
public class FaturamentoResponseDTO {

    private BigDecimal hoje;
    private BigDecimal totalSemana;
    private BigDecimal totalQuinzena;
    private BigDecimal totalMensal;

    public FaturamentoResponseDTO() {
    }

    public FaturamentoResponseDTO(BigDecimal hoje, BigDecimal totalSemana, BigDecimal totalQuinzena, BigDecimal totalMensal) {
        this.hoje = hoje;
        this.totalSemana = totalSemana;
        this.totalQuinzena = totalQuinzena;
        this.totalMensal = totalMensal;
    }

    public BigDecimal getHoje() {
        return hoje;
    }

    public void setHoje(BigDecimal hoje) {
        this.hoje = hoje;
    }

    public BigDecimal getTotalSemana() {
        return totalSemana;
    }

    public void setTotalSemana(BigDecimal totalSemana) {
        this.totalSemana = totalSemana;
    }

    public BigDecimal getTotalQuinzena() {
        return totalQuinzena;
    }

    public void setTotalQuinzena(BigDecimal totalQuinzena) {
        this.totalQuinzena = totalQuinzena;
    }

    public BigDecimal getTotalMensal() {
        return totalMensal;
    }

    public void setTotalMensal(BigDecimal totalMensal) {
        this.totalMensal = totalMensal;
    }
}
