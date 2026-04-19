package com.br.davyson.GerenciamentoPedidos.controllers;

import com.br.davyson.GerenciamentoPedidos.dto.response.CartaoClienteResponseDTO;
import com.br.davyson.GerenciamentoPedidos.enums.BandeiraCartao;
import com.br.davyson.GerenciamentoPedidos.enums.ModalidadaCartao;
import com.br.davyson.GerenciamentoPedidos.services.CartaoClienteService;
import com.br.davyson.GerenciamentoPedidos.wrapper.ListWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cartoes")
@Tag(name = "Cartões de teste", description = "Cartões de teste para registros pagamentos de forma segura")
@SecurityRequirement(name = "bearerAuth")
public class CartaoClienteController {
    private final CartaoClienteService service;

    public CartaoClienteController(CartaoClienteService service) {
        this.service = service;
    }

    @Operation(summary = "Registrar cartões", description = "!Apenas administradores!")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/registrar-cartao/{saldo}")
    public ResponseEntity<CartaoClienteResponseDTO> registrarCartao(
                                                                    @RequestParam(value = "senha") String senha,
                                                                    @PathVariable BigDecimal saldo,
                                                                    BandeiraCartao bandeira,
                                                                    ModalidadaCartao modalidade){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarCartao(senha,saldo,bandeira,modalidade));
    }

    @Operation(summary = "Listar todos os cartões cadastrados", description = "Autenticação necessária")
    @GetMapping
    public ResponseEntity<ListWrapper<CartaoClienteResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarCartoes());
    }

    @Operation(summary = "Buscar um cartão específico pelo ID", description = "Autenticação necessária")
    @GetMapping("/{id}")
    public ResponseEntity<CartaoClienteResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Deletar Cartão pelo id", description = "!Apenas administradores!")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarCartao(@PathVariable Long id){
        service.deletarCartao(id);
        return ResponseEntity.noContent().build();
    }
}
