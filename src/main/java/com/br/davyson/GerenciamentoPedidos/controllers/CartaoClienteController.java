package com.br.davyson.GerenciamentoPedidos.controllers;

import com.br.davyson.GerenciamentoPedidos.dto.CartaoClienteResponseDTO;
import com.br.davyson.GerenciamentoPedidos.services.CartaoClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartoes")
@Tag(name = "Cartões de teste", description = "Cartões de teste para registros pagamentos de forma segura")
public class CartaoClienteController {
    private final CartaoClienteService service;

    public CartaoClienteController(CartaoClienteService service) {
        this.service = service;
    }
    @Operation(summary = "Listar todos os cartões cadastrados")
    @GetMapping
    public ResponseEntity<List<CartaoClienteResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @Operation(summary = "Buscar um cartão específico pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<CartaoClienteResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
}
