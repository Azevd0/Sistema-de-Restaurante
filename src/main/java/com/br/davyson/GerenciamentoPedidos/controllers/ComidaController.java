package com.br.davyson.GerenciamentoPedidos.controllers;

import com.br.davyson.GerenciamentoPedidos.dto.request.ComidaRequestDTO;
import com.br.davyson.GerenciamentoPedidos.dto.response.ComidaResponseDTO;
import com.br.davyson.GerenciamentoPedidos.services.ComidaService;
import com.br.davyson.GerenciamentoPedidos.wrapper.ListWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comida")
@Tag(name = "Cardápio", description = "Gerenciamento dos itens do cardápio do restaurante")
@SecurityRequirement(name = "bearerAuth")
public class ComidaController {
    private final ComidaService comidaService;

    public ComidaController(ComidaService comidaService) {
        this.comidaService = comidaService;
    }

    @Operation(summary = "Listar todo o cardápio")
    @GetMapping
    public ResponseEntity<ListWrapper<ComidaResponseDTO>> listarMenu() {
        ListWrapper<ComidaResponseDTO> response = comidaService.openMenu();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar comida por nome ou palavra-chave")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<ListWrapper<ComidaResponseDTO>> buscarPorNome(@PathVariable String nome) {
        ListWrapper<ComidaResponseDTO> dtos = comidaService.findComida(nome);
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Listar comidas por categoria")
    @GetMapping("/categoria/{nome}")
    public ResponseEntity<ListWrapper<ComidaResponseDTO>> listarPorCategoria(@PathVariable String nome) {
        ListWrapper<ComidaResponseDTO> dtos = comidaService.findComidaByCategoria(nome);
        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Registrar nova comida", description = "!Apenas administradores!")
    @PostMapping
    public ResponseEntity<ComidaResponseDTO> cadastrar(@RequestParam(value = "categoria") String categoriaNome, @Valid @RequestBody ComidaRequestDTO comida) {
        ComidaResponseDTO novaComida = comidaService.saveFood(comida, categoriaNome);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaComida);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar comida", description = "!Apenas administradores!")
    @PutMapping("/{nome}")
    public ResponseEntity<ComidaResponseDTO> atualizar(@PathVariable String nome, @RequestBody ComidaRequestDTO dto) {

        ComidaResponseDTO comidaAtualizada = comidaService.updateFood(nome, dto);
        return ResponseEntity.ok(comidaAtualizada);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Excluir Comida", description = "!Apenas administradores!")
    @DeleteMapping("/{nome}")
    public ResponseEntity<Void> deletar(@PathVariable String nome) {
        comidaService.deleteFood(nome);
        return ResponseEntity.noContent().build();
    }
}
