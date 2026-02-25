package com.br.davyson.GerenciamentoPedidos.controllers;

import com.br.davyson.GerenciamentoPedidos.dto.ComidaRequestDTO;
import com.br.davyson.GerenciamentoPedidos.dto.ComidaResponseDTO;
import com.br.davyson.GerenciamentoPedidos.entitys.Comida;
import com.br.davyson.GerenciamentoPedidos.services.ComidaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comida")
@Tag(name = "Cardápio", description = "Gerenciamento dos itens do cardápio do restaurante")
public class ComidaController {
    private final ComidaService comidaService;

    public ComidaController(ComidaService comidaService) {
        this.comidaService = comidaService;
    }

    @Operation(summary = "Listar todo o cardápio")
    @GetMapping
    public ResponseEntity<List<ComidaResponseDTO>> listarMenu() {
        List<ComidaResponseDTO> dtos = comidaService.openMenu().stream()
                .map(ComidaResponseDTO::new)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Buscar comida por ID")
    @GetMapping("/id/{id}")
    public ResponseEntity<ComidaResponseDTO> buscarPorId(@PathVariable Long id) {
        Comida comida = comidaService.findById(id);
        return ResponseEntity.ok(new ComidaResponseDTO(comida));
    }

    @Operation(summary = "Buscar comida por nome ou palavra-chave")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<ComidaResponseDTO>> buscarPorNome(@PathVariable String nome) {
        List<ComidaResponseDTO> dtos = comidaService.findComida(nome).stream()
                .map(ComidaResponseDTO::new)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Listar comidas por categoria")
    @GetMapping("/categoria/{nome}")
    public ResponseEntity<List<ComidaResponseDTO>> listarPorCategoria(@PathVariable String nome) {
        List<ComidaResponseDTO> dtos = comidaService.findComidaByCategoria(nome).stream()
                .map(ComidaResponseDTO::new)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Cadastrar nova comida")
    @PostMapping
    public ResponseEntity<ComidaResponseDTO> cadastrar(@RequestParam(value = "categoria") String categoriaNome, @Valid @RequestBody Comida comida) {
        Comida novaComida = comidaService.saveFood(comida, categoriaNome);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ComidaResponseDTO(novaComida));
    }

    @Operation(summary = "Atualizar comida")
    @PutMapping("/{nome}")
    public ResponseEntity<ComidaResponseDTO> atualizar(@PathVariable String nome, @RequestBody ComidaRequestDTO dto) {

        Comida comidaAtualizada = comidaService.updateFood(nome, dto);
        return ResponseEntity.ok(new ComidaResponseDTO(comidaAtualizada));
    }

    @Operation(summary = "Excluir Comida")
    @DeleteMapping("/{nome}")
    public ResponseEntity<Void> deletar(@PathVariable String nome) {
        comidaService.deleteFood(nome);
        return ResponseEntity.noContent().build();
    }
}
