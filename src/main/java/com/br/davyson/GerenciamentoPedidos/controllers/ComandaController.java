package com.br.davyson.GerenciamentoPedidos.controllers;

import com.br.davyson.GerenciamentoPedidos.dto.response.ComandaDTO;
import com.br.davyson.GerenciamentoPedidos.enums.Periodo;
import com.br.davyson.GerenciamentoPedidos.services.ComandaService;
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
@RequestMapping("/comandas")
@Tag(name = "Histórico de lançamento", description = "Consulta do histórico de itens lançados.")
@SecurityRequirement(name = "bearerAuth")
public class ComandaController {
    private final ComandaService comandaService;

    public ComandaController(ComandaService comandaService) {
        this.comandaService = comandaService;
    }

    @Operation(summary = "Listar todo o histórico de lançamentos filtrado por período."
            , description = "Autenticação necessária")
    @GetMapping("/itens_lancados")
    public ResponseEntity<ListWrapper<ComandaDTO>> listarHistorico(@RequestParam Periodo periodo) {
        ListWrapper<ComandaDTO> historico = comandaService.listarHistorico(periodo);
        return ResponseEntity.ok(historico);
    }
}
