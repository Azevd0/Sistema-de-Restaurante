package com.br.davyson.GerenciamentoPedidos.controllers;
import com.br.davyson.GerenciamentoPedidos.entitys.Atendente;
import com.br.davyson.GerenciamentoPedidos.services.AtendenteService;
import com.br.davyson.GerenciamentoPedidos.dto.AtendenteResponseDTO;
import com.br.davyson.GerenciamentoPedidos.dto.AtendenteRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/atendente")
@Tag(name = "Atendentes", description = "Gerenciamento dos funcionários")

public class AtendenteController {

    private final AtendenteService atendenteService;

    public AtendenteController(AtendenteService atendenteService) {
        this.atendenteService = atendenteService;
    }

    @Operation(summary = "Listar todos os atendentes")
    @GetMapping
    public ResponseEntity<List<AtendenteResponseDTO>> listarTodos() {
        List<AtendenteResponseDTO> list = atendenteService.listAll()
                .stream()
                .map(AtendenteResponseDTO::new)
                .toList();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Buscar atendente por nome")
    @GetMapping("/busca/{nome}")
    public ResponseEntity<AtendenteResponseDTO> buscarPorNome(@PathVariable String nome) {
        Atendente atendente = atendenteService.searchForName(nome);
        return ResponseEntity.ok(new AtendenteResponseDTO(atendente));
    }

    @Operation(summary = "Cadastrar atendente")
    @PostMapping("/cadastro")
    public ResponseEntity<AtendenteResponseDTO> cadastrar(@RequestBody @Valid AtendenteRequestDTO dto) {
        Atendente atendente = new Atendente();
        atendente.setNome(dto.nome());
        atendente.setLogin(dto.login());
        atendente.setSenha(dto.senha());

        Atendente salvo = atendenteService.saveAtendente(atendente);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AtendenteResponseDTO(salvo));
    }

    @Operation(summary = "Atualizar atendente")
    @PutMapping("/atualizacao/{nome}")
    public ResponseEntity<AtendenteResponseDTO> atualizar(@PathVariable String nome, @RequestBody @Valid AtendenteRequestDTO dto) {
        Atendente atualizado = atendenteService.updateAtendenteByName(nome, dto);
        return ResponseEntity.ok(new AtendenteResponseDTO(atualizado));
    }

    @Operation(summary = "Remover atendente")
    @DeleteMapping("/remover/{nome}")
    public ResponseEntity<Void> deletar(@PathVariable String nome) {
        atendenteService.deleteUser(nome);
        return ResponseEntity.noContent().build();
    }
}
