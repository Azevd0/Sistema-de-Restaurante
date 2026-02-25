package com.br.davyson.GerenciamentoPedidos.controllers;

import com.br.davyson.GerenciamentoPedidos.dto.CategoriaResponseDTO;
import com.br.davyson.GerenciamentoPedidos.entitys.Categoria;
import com.br.davyson.GerenciamentoPedidos.services.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
@Tag(name = "Categorias", description = "Organização das comidas por suas categorias")
public class CategoriaController {
    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todas as categorias")
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodas() {
        List<CategoriaResponseDTO> dtos = service.ListAll().stream()
                .map(CategoriaResponseDTO::new)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Buscar categoria por ID")
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(@PathVariable Long id) {
        Categoria categoria = service.findById(id);
        return ResponseEntity.ok(new CategoriaResponseDTO(categoria));
    }

    @Operation(summary = "Buscar categoria por nome")
    @GetMapping("/buscar")
    public ResponseEntity<CategoriaResponseDTO> buscarPorNome(@RequestParam String nome) {
        Categoria categoria = service.findByNome(nome);
        return ResponseEntity.ok(new CategoriaResponseDTO(categoria));
    }

    @Operation(summary = "Cadastrar nova categoria")
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> cadastrar(@Valid @RequestBody Categoria categoria) {
        Categoria novaCategoria = service.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CategoriaResponseDTO(novaCategoria));
    }

    @Operation(summary = "Atualizar categoria pelo nome")
    @PutMapping("/{nome}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable String nome, @Valid @RequestBody Categoria categoria) {
        Categoria categoriaAtualizada = service.updateByNome(nome, categoria);
        return ResponseEntity.ok(new CategoriaResponseDTO(categoriaAtualizada));
    }

    @Operation(summary = "Excluir categoria")
    @DeleteMapping("/{nome}")
    public ResponseEntity<Void> deletar(@PathVariable String nome) {
        service.deleteByNome(nome);
        return ResponseEntity.noContent().build();
    }
}

