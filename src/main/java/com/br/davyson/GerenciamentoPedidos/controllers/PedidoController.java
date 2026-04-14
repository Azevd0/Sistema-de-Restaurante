package com.br.davyson.GerenciamentoPedidos.controllers;

import com.br.davyson.GerenciamentoPedidos.dto.request.PedidoRequestDTO;
import com.br.davyson.GerenciamentoPedidos.dto.response.PedidoResponseDTO;
import com.br.davyson.GerenciamentoPedidos.enums.FormaPagamento;
import com.br.davyson.GerenciamentoPedidos.services.PedidoService;
import com.br.davyson.GerenciamentoPedidos.wrapper.ListWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos", description = "Gerenciamento dos pedidos do restaurante")
@SecurityRequirement(name = "bearerAuth")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Operation(summary = "Listar todos os pedidos pendentes")
    @GetMapping("/pendencias")
    public ResponseEntity<ListWrapper<PedidoResponseDTO>> listarPendentes() {
        return ResponseEntity.ok(pedidoService.buscarPedidosPendentes());
    }

    @Operation(summary = "Buscar pedido de uma mesa específica")
    @GetMapping("/buscar-mesa/{mesa}")
    public ResponseEntity<PedidoResponseDTO> buscarPorMesa(@PathVariable Integer mesa) {
        PedidoResponseDTO pedido = pedidoService.buscarDTOPorMesa(mesa);
        return ResponseEntity.ok(pedido);
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
            @PathVariable String nomeComida,
            @RequestParam(defaultValue = "''", value = "observacao") String observacao) {
        return ResponseEntity.ok(pedidoService.adicionarComida(mesa, nomeComida, observacao));
    }

    @Operation(summary = "Registrar pagamento")
    @PatchMapping("/pagamento/{mesa}")
    public ResponseEntity<Object> registrarPagamento(@PathVariable Integer mesa, @RequestParam(value = "cartaoId", defaultValue = "0") Long cartaoId,
                                                     @RequestParam(value = "valor") BigDecimal valor, @RequestParam(value = "modalidade") FormaPagamento modalidade
            ,@RequestParam(value = "cartaoSenha", defaultValue = "''") String senhaCartao, @RequestParam(value = "qtdPessoas", defaultValue = "1") Integer qtdPessoas) {
        Object pedidoFechado = pedidoService.registrarPagamento(mesa, valor,cartaoId, modalidade, qtdPessoas, senhaCartao);
        return ResponseEntity.ok(pedidoFechado);
    }
    //http://localhost:9090/pedidos/pagamento/61?cartaoId=0&valor=200.09&modalidade=PIX&cartaoSenha=''&qtdPessoas=3
    @Operation(summary = "Remover um item específico do pedido")
    @DeleteMapping("/{mesa}/remover-item")
    public ResponseEntity<Void> removerItem(
            @PathVariable Integer mesa,
            @RequestParam String nome) {
        pedidoService.cancelarComida(mesa, nome);
        return ResponseEntity.noContent().build();
    }
}
