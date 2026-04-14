package com.br.davyson.GerenciamentoPedidos.controllers;

import com.br.davyson.GerenciamentoPedidos.dto.response.CategoriaResponseDTO;
import com.br.davyson.GerenciamentoPedidos.entitys.Categoria;
import com.br.davyson.GerenciamentoPedidos.services.CategoriaService;
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
@RequestMapping("/categoria")
@Tag(name = "Categorias", description = "Organização das comidas por suas categorias")
@SecurityRequirement(name = "bearerAuth")
public class CategoriaController {
    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todas as categorias")
    @GetMapping
    public ResponseEntity<ListWrapper<CategoriaResponseDTO>> listarTodas() {
        ListWrapper<CategoriaResponseDTO> dtos = service.ListAll();
        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Registrar nova categoria")
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> cadastrar(@Valid @RequestBody Categoria categoria) {
        CategoriaResponseDTO novaCategoria = service.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar categoria pelo nome")
    @PutMapping("/{nome}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable String nome, @Valid @RequestBody Categoria categoria) {
        CategoriaResponseDTO categoriaAtualizada = service.updateByNome(nome, categoria);
        return ResponseEntity.ok(categoriaAtualizada);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Excluir categoria")
    @DeleteMapping("/{nome}")
    public ResponseEntity<Void> deletar(@PathVariable String nome) {
        service.deleteByNome(nome);
        return ResponseEntity.noContent().build();
    }
}

