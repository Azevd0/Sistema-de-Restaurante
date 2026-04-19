package com.br.davyson.GerenciamentoPedidos.controllers;

import com.br.davyson.GerenciamentoPedidos.dto.response.FaturamentoResponseDTO;
import com.br.davyson.GerenciamentoPedidos.dto.response.ReciboResponseDTO;
import com.br.davyson.GerenciamentoPedidos.enums.Periodo;
import com.br.davyson.GerenciamentoPedidos.services.ReciboService;
import com.br.davyson.GerenciamentoPedidos.wrapper.ListWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recibo")
@Tag(name = "Histórico Financeiro", description = "Relatório de vendas")
@SecurityRequirement(name = "bearerAuth")
public class ReciboController {
    private final ReciboService reciboService;

    public ReciboController(ReciboService reciboService) {
        this.reciboService = reciboService;
    }

    @Operation(summary = "Ver faturamento de todos os períodos", description = "Autenticação necessária")
    @GetMapping("/faturamento")
    public ResponseEntity<FaturamentoResponseDTO> verFaturamento() {
        return ResponseEntity.ok(reciboService.calcularFaturamento());
    }

    @Operation(summary = "Filtrar histórico por período", description = "Autenticação necessária")
    @GetMapping("/historico-de-vendas")
    public ResponseEntity<ListWrapper<ReciboResponseDTO>> obterHistorico(
            @RequestParam Periodo periodo) {
        return ResponseEntity.ok(reciboService.listarHistorico(periodo));
    }
}
