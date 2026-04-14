package com.br.davyson.GerenciamentoPedidos.controllers;
import com.br.davyson.GerenciamentoPedidos.dto.response.PedidoResponseDTO;
import com.br.davyson.GerenciamentoPedidos.entitys.Atendente;
import com.br.davyson.GerenciamentoPedidos.services.AtendenteService;
import com.br.davyson.GerenciamentoPedidos.dto.response.AtendenteRegisterResponse;
import com.br.davyson.GerenciamentoPedidos.dto.request.AtendenteRegisterRequest;
import com.br.davyson.GerenciamentoPedidos.wrapper.ListWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atendente")
@Tag(name = "Atendentes", description = "Gerenciamento dos funcionários")
@SecurityRequirement(name = "bearerAuth")
public class AtendenteController {

    private final AtendenteService atendenteService;

    public AtendenteController(AtendenteService atendenteService) {
        this.atendenteService = atendenteService;
    }

    @Operation(summary = "Buscar atendente por nome")
    @GetMapping("/busca/{nome}")
    public ResponseEntity<AtendenteRegisterResponse> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(atendenteService.buscarPorNome(nome));
    }

    @Operation(summary = "Listar pedidos do atendente pelo id")
    @GetMapping("/listarPedidos/{id}")
    public ResponseEntity<ListWrapper<PedidoResponseDTO>> listarPedidosDoAtendente(@PathVariable Long id){
        Atendente atendete = atendenteService.findById(id);
        ListWrapper<PedidoResponseDTO> pedidos = atendenteService.listarPedidosDoAtendente(atendete);
        return ResponseEntity.ok(pedidos);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar atendente")
    @PutMapping("/atualizacao/{nome}")
    public ResponseEntity<AtendenteRegisterResponse> atualizar(@PathVariable String nome, @RequestBody @Valid AtendenteRegisterRequest dto) {
        AtendenteRegisterResponse atualizado = atendenteService.updateAtendenteByName(nome, dto);
        return ResponseEntity.ok(atualizado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remover atendente")
    @DeleteMapping("/remover/{nome}")
    public ResponseEntity<Void> deletar(@PathVariable String nome) {
        atendenteService.deleteUser(nome);
        return ResponseEntity.noContent().build();
    }
}
