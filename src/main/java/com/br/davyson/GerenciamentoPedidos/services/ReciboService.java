package com.br.davyson.GerenciamentoPedidos.services;

import com.br.davyson.GerenciamentoPedidos.dto.FaturamentoResponseDTO;
import com.br.davyson.GerenciamentoPedidos.dto.ReciboResponseDTO;
import com.br.davyson.GerenciamentoPedidos.entitys.Recibo;
import com.br.davyson.GerenciamentoPedidos.enums.Fatura;
import com.br.davyson.GerenciamentoPedidos.repositorys.ReciboRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReciboService {
    private final ReciboRepository reciboRepository;

    public ReciboService(ReciboRepository reciboRepository) {
        this.reciboRepository = reciboRepository;
    }

    public List<ReciboResponseDTO> listarHistorico(Fatura periodo) {
        LocalDateTime dataLimite = switch (periodo) {
            case TOTAL_HOJE -> LocalDate.now().atStartOfDay();
            case TOTAL_SEMANA -> LocalDateTime.now().minusWeeks(1);
            case TOTAL_QUINZENA -> LocalDateTime.now().minusWeeks(2);
            case TOTAL_MENSAL -> LocalDateTime.now().minusMonths(1);
        };
        return reciboRepository.findByDataFechamentoAfter(dataLimite).stream().map(ReciboResponseDTO::new).toList();
    }

    private BigDecimal somarPorPeriodo(LocalDateTime data) {
        return reciboRepository.findByDataFechamentoAfter(data)
                .stream()
                .map(Recibo::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public FaturamentoResponseDTO calcularFaturamento() {
        BigDecimal hoje = somarPorPeriodo(LocalDate.now().atStartOfDay());
        BigDecimal semana = somarPorPeriodo(LocalDateTime.now().minusWeeks(1));
        BigDecimal quinzena = somarPorPeriodo(LocalDateTime.now().minusWeeks(2));
        BigDecimal mensal = somarPorPeriodo(LocalDateTime.now().minusMonths(1));

        return new FaturamentoResponseDTO(hoje,semana, quinzena, mensal);
    }
}
