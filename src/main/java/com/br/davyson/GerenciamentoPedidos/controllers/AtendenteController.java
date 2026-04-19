package com.br.davyson.GerenciamentoPedidos.controllers;
import com.br.davyson.GerenciamentoPedidos.dto.response.PedidoResponseDTO;
import com.br.davyson.GerenciamentoPedidos.entitys.Atendente;
import com.br.davyson.GerenciamentoPedidos.enums.Role;
import com.br.davyson.GerenciamentoPedidos.services.AtendenteService;
import com.br.davyson.GerenciamentoPedidos.dto.response.AtendenteRegisterResponse;
import com.br.davyson.GerenciamentoPedidos.wrapper.ListWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Operation(summary = "Buscar atendente por nome", description = "Autenticação necessária")
    @GetMapping("/busca/{nome}")
    public ResponseEntity<AtendenteRegisterResponse> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(atendenteService.buscarPorNome(nome));
    }

    @Operation(summary = "Listar pedidos do atendente pelo id", description = "Autenticação necessária")
    @GetMapping("/listarPedidos/{id}")
    public ResponseEntity<ListWrapper<PedidoResponseDTO>> listarPedidosDoAtendente(@PathVariable Long id){
        Atendente atendete = atendenteService.findById(id);
        ListWrapper<PedidoResponseDTO> pedidos = atendenteService.listarPedidosDoAtendente(atendete);
        return ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Listar todos os funcionários", description = "!Apenas administradores!")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/funcionarios")
    public ResponseEntity<ListWrapper<AtendenteRegisterResponse>> listarTodosOsAtendentes(){
        return ResponseEntity.ok(atendenteService.listarTodosOsAtendentes());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Promover atendente", description = "!Apenas administradores!")
    @PatchMapping("/promover/{id}")
    public ResponseEntity<AtendenteRegisterResponse> promover(@PathVariable Long id, @RequestParam Role cargo){
        return ResponseEntity.ok(atendenteService.promoverAtendente(id,cargo));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remover atendente", description = "!Apenas administradores!")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        atendenteService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
