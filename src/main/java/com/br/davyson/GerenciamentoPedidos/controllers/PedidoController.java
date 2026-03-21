package com.br.davyson.GerenciamentoPedidos.controllers;

import com.br.davyson.GerenciamentoPedidos.dto.PedidoRequestDTO;
import com.br.davyson.GerenciamentoPedidos.dto.PedidoResponseDTO;
import com.br.davyson.GerenciamentoPedidos.entitys.Pedido;
import com.br.davyson.GerenciamentoPedidos.enums.FormaPagamento;
import com.br.davyson.GerenciamentoPedidos.services.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.math.BigDecimal;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos", description = "Gerenciamento dos pedidos do restaurante")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Operation(summary = "Listar todos os pedidos pendentes (Não pagos)")
    @GetMapping("/pendencias")
    public ResponseEntity<List<PedidoResponseDTO>> listarPendentes() {
        return ResponseEntity.ok(pedidoService.buscarPedidosPendentes());
    }

    @Operation(summary = "Buscar pedido de uma mesa específica")
    @GetMapping("/buscar-mesa/{mesa}")
    public ResponseEntity<PedidoResponseDTO> buscarPorMesa(@PathVariable Integer mesa) {
        Pedido pedido = pedidoService.buscarPorMesa(mesa);
        return ResponseEntity.ok(new PedidoResponseDTO(pedido));
    }

    @Operation(summary = "Lançar pedido")
    @PostMapping("/lancamentoPedido")
    public ResponseEntity<PedidoResponseDTO> lancarPedido(@Valid @RequestBody PedidoRequestDTO requestDTO){
        PedidoResponseDTO response = pedidoService.lancarPedido(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Mudar número da mesa do pedido")
    @PutMapping("/alterar-mesa/{atual}/{nova}")
    public ResponseEntity<PedidoResponseDTO> mudarMesa(
            @PathVariable Integer atual,
            @PathVariable Integer nova) {
        return ResponseEntity.ok(pedidoService.alterarMesa(atual, nova));
    }
    @Operation(summary = "Transferir um item de uma mesa para outra")
    @PatchMapping("/transferir/{origem}/{destino}/{comida}")
    public ResponseEntity<PedidoResponseDTO> transferir(
            @PathVariable Integer origem,
            @PathVariable Integer destino,
            @PathVariable String comida) {
        return ResponseEntity.ok(pedidoService.transferirComida(origem, destino, comida));
    }
    @Operation(summary = "Adicionar uma comida a um pedido existente")
    @PatchMapping("/adicionar/{mesa}/{nomeComida}")
    public ResponseEntity<PedidoResponseDTO> adicionarItem(
            @PathVariable Integer mesa,
            @PathVariable String nomeComida) {
        return ResponseEntity.ok(pedidoService.adicionarComida(mesa, nomeComida));
    }

    @Operation(summary = "Registrar pagamento")
    @PatchMapping("/pagamento/{mesa}/{cartaoId}")
    public ResponseEntity<Object> registrarPagamento(@PathVariable Integer mesa, @PathVariable Long cartaoId,
                                                     @RequestParam BigDecimal valor, @RequestParam FormaPagamento modalidade
            ,@RequestParam String senhaCartao, @RequestParam Integer qtdPessoas) {
        Object pedidoFechado = pedidoService.registrarPagamento(mesa, valor,cartaoId, modalidade, qtdPessoas, senhaCartao);
        return ResponseEntity.ok(pedidoFechado);
    }

    @Operation(summary = "Remover um item específico do pedido")
    @DeleteMapping("/{mesa}/remover-item")
    public ResponseEntity<Void> removerItem(
            @PathVariable Integer mesa,
            @RequestParam String nome) {
        pedidoService.cancelarComida(mesa, nome);
        return ResponseEntity.noContent().build();
    }
}
