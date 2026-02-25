package com.br.davyson.GerenciamentoPedidos.services;

import com.br.davyson.GerenciamentoPedidos.dto.PedidoResponseDTO;
import com.br.davyson.GerenciamentoPedidos.entitys.Comida;
import com.br.davyson.GerenciamentoPedidos.entitys.Pedido;
import com.br.davyson.GerenciamentoPedidos.exceptions.ObjectNotFoundException;
import com.br.davyson.GerenciamentoPedidos.repositorys.AtendenteRepository;
import com.br.davyson.GerenciamentoPedidos.repositorys.ComidaRepository;
import com.br.davyson.GerenciamentoPedidos.repositorys.PedidoRepository;
import com.br.davyson.GerenciamentoPedidos.repositorys.ReciboRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ComidaRepository comidaRepository;
    private final AtendenteRepository atendenteRepository;
    private final ReciboRepository reciboRepository;
    private final AtendenteService atendenteService;

    public PedidoService(PedidoRepository pedidoRepository, ComidaRepository comidaRepository, AtendenteRepository atendenteRepository, ReciboRepository reciboRepository,AtendenteService atendenteService) {
        this.pedidoRepository = pedidoRepository;
        this.comidaRepository = comidaRepository;
        this.atendenteRepository = atendenteRepository;
        this.reciboRepository = reciboRepository;
        this.atendenteService = atendenteService;
    }

//    @Transactional
//    public PedidoResponseDTO lancarPedido(PedidoRequestDTO dto) {
//        String nomeUsuarioLogado = "Davyson de Azevedo";
//        Atendente atendente = atendenteService.searchForName(nomeUsuarioLogado);
//
//        List<Comida> comidas = dto.nomesComidas().stream()
//                .map(nome -> comidaRepository.findByNomeIgnoreCase(nome)
//                        .orElseThrow(() -> new ObjectNotFoundException("Comida '" + nome + "' não encontrada.")))
//                .toList();
//
//        Pedido novoPedido = new Pedido(dto, atendente);
//        novoPedido.setComidas(comidas);
//        novoPedido.setObservacao(dto.observacao());
//        novoPedido.setStatusPagamento(false);
//
//        Pedido pedidoSalvo = pedidoRepository.save(novoPedido);
//        return new PedidoResponseDTO(pedidoSalvo);
//    }

    public Pedido buscarPorMesa(Integer mesa) {
        return pedidoRepository.findByMesa(mesa)
                .orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado para a mesa " + mesa));
    }

    public List<PedidoResponseDTO> buscarPedidosPendentes(){
        return pedidoRepository.findByStatusPagamentoFalse().stream()
                .map(PedidoResponseDTO::new)
                .toList();
    }
    @Transactional
    public PedidoResponseDTO transferirComida(Integer mesaOrigem, Integer mesaDestino, String nomeComida) {
        Pedido pedidoOrigem = buscarPorMesa(mesaOrigem);
        Pedido pedidoDestino = buscarPorMesa(mesaDestino);

        Comida comidaTransferida = pedidoOrigem.getComidas().stream()
                .filter(c -> c.getNome().equalsIgnoreCase(nomeComida))
                .findFirst()
                .orElseThrow(() -> new ObjectNotFoundException(
                        "A comida '" + nomeComida + "' não foi encontrada na mesa " + mesaOrigem));

        pedidoOrigem.getComidas().remove(comidaTransferida);
        pedidoDestino.getComidas().add(comidaTransferida);

        pedidoRepository.save(pedidoOrigem);
        Pedido pedidoAtualizado = pedidoRepository.save(pedidoDestino);
        return new PedidoResponseDTO(pedidoAtualizado);
    }

    @Transactional
    public PedidoResponseDTO alterarMesa(Integer mesaAtual, Integer novaMesa) {
        Pedido pedido = buscarPorMesa(mesaAtual);
        pedido.setMesa(novaMesa);
        return new PedidoResponseDTO(pedidoRepository.save(pedido));
    }

    @Transactional
    public PedidoResponseDTO registrarPagamento(Integer mesa, BigDecimal valorRecebido) {
        Pedido pedido = buscarPorMesa(mesa);

        pedido.setValorPago(pedido.getValorPago().add(valorRecebido));

        BigDecimal totalComTaxa = pedido.getValorTotal();
        BigDecimal subtotalSemTaxa = pedido.getSubtotal();
        BigDecimal totalPago = pedido.getValorPago();

        if (totalPago.compareTo(totalComTaxa) >= 0 || totalPago.compareTo(subtotalSemTaxa) >= 0) {
            pedido.setStatusPagamento(true);
        }
        return new PedidoResponseDTO(pedidoRepository.save(pedido));
    }
}
