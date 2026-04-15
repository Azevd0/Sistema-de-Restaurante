package com.br.davyson.GerenciamentoPedidos.services;

import com.br.davyson.GerenciamentoPedidos.dto.response.FaturamentoResponseDTO;
import com.br.davyson.GerenciamentoPedidos.entitys.Recibo;
import com.br.davyson.GerenciamentoPedidos.repositorys.ReciboRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class ReciboServiceTest {

    private ReciboRepository reciboRepository;
    private ReciboService reciboService;

    @BeforeEach
    void setUp() {
        reciboRepository = Mockito.mock(ReciboRepository.class);
        reciboService = new ReciboService(reciboRepository);
    }

    @Test
    @DisplayName("Calcular o faturamento por período")
    void deveRetornarSomaCorretaParaOsPeriodos() {
        Recibo recibo1 = new Recibo();
        recibo1.setValorTotal(BigDecimal.valueOf(100.0));

        Recibo recibo2 = new Recibo();
        recibo2.setValorTotal(BigDecimal.valueOf(50.0));

        Mockito.when(reciboRepository.findByDataFechamentoAfter(Mockito.any(LocalDateTime.class)))
                .thenReturn(List.of(recibo1, recibo2));

        FaturamentoResponseDTO faturamento = reciboService.calcularFaturamento();

        assertEquals(BigDecimal.valueOf(150.0), faturamento.getHoje());
        assertEquals(BigDecimal.valueOf(150.0), faturamento.getTotalSemana());
        assertEquals(BigDecimal.valueOf(150.0), faturamento.getTotalQuinzena());
        assertEquals(BigDecimal.valueOf(150.0), faturamento.getTotalMensal());

        // Verificação final de interações
        Mockito.verify(reciboRepository, Mockito.times(4))
                .findByDataFechamentoAfter(Mockito.any(LocalDateTime.class));
    }
}