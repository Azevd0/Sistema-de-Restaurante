package com.br.davyson.GerenciamentoPedidos.services;

import com.br.davyson.GerenciamentoPedidos.dto.PedidoRequestDTO;
import com.br.davyson.GerenciamentoPedidos.dto.PedidoResponseDTO;
import com.br.davyson.GerenciamentoPedidos.dto.ReciboResponseDTO;
import com.br.davyson.GerenciamentoPedidos.entitys.*;
import com.br.davyson.GerenciamentoPedidos.enums.BandeiraCartao;
import com.br.davyson.GerenciamentoPedidos.enums.FormaPagamento;
import com.br.davyson.GerenciamentoPedidos.exceptions.ObjectNotFoundException;
import com.br.davyson.GerenciamentoPedidos.repositorys.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ComidaRepository comidaRepository;
    private final ReciboRepository reciboRepository;
    private final AtendenteService atendenteService;
    private final ComidaService comidaService;
    private final ComandaRepository comandaRepository;
    private final CartaoClienteRepository cartaoClienteRepository;

    public PedidoService(PedidoRepository pedidoRepository, ComidaRepository comidaRepository, ReciboRepository reciboRepository, AtendenteService atendenteService, ComidaService comidaService, ComandaRepository comandaRepository, CartaoClienteRepository cartaoClienteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.comidaRepository = comidaRepository;
        this.reciboRepository = reciboRepository;
        this.atendenteService = atendenteService;
        this.comidaService = comidaService;
        this.comandaRepository = comandaRepository;
        this.cartaoClienteRepository = cartaoClienteRepository;
    }

    @Transactional
    public PedidoResponseDTO lancarPedido(PedidoRequestDTO dto) {
        String nomeUsuario = "José Eduardo";
        Atendente atendente = atendenteService.buscarPorNome(nomeUsuario);

        if (pedidoRepository.existsByMesa(dto.numeroMesa())) {
            throw new DataIntegrityViolationException("A Mesa " + dto.numeroMesa() + " já possui um pedido em aberto!");
        }
        List<Comida> comidas = dto.nomesComidas().stream()
                .map(nome -> comidaRepository.findByNomeIgnoreCase(nome)
                        .orElseThrow(() -> new ObjectNotFoundException("Comida '" + nome + "' não encontrada.")))
                .toList();

        Pedido novoPedido = new Pedido();
        novoPedido.setAtendente(atendente);
        novoPedido.setMesa(dto.numeroMesa());
        novoPedido.setComidas(comidas);
        novoPedido.setObservacao(dto.observacao());
        novoPedido.setStatusPagamento(false);

        Pedido pedidoSalvo = pedidoRepository.save(novoPedido);

            Comanda comanda = new Comanda();
            comanda.setMesa(pedidoSalvo.getMesa());
            comanda.setAtendenteNome(atendente.getNome());
            comanda.setComidaNome(comidas.stream().map(Comida::getNome).toList());
            comandaRepository.save(comanda);


        return new PedidoResponseDTO(pedidoSalvo);
    }
    @Transactional
    public PedidoResponseDTO adicionarComida(Integer mesa, String nomeComida) {
        Pedido pedido = buscarPorMesa(mesa);

        if (pedido.getStatusPagamento()) {
            throw new DataIntegrityViolationException("Não é possível adicionar itens. O pedido da mesa " + mesa + " já está fechado.");
        }
            Comida novaComida = comidaRepository.findByNomeIgnoreCase(nomeComida)
                    .orElseThrow(() -> new ObjectNotFoundException("Comida '" + nomeComida + "' não encontrada no cardápio."));

            pedido.getComidas().add(novaComida);
            Comanda comanda = new Comanda();
            comanda.setMesa(pedido.getMesa());
            comanda.setAtendenteNome(pedido.getAtendente().getNome());
            comanda.getComidaNome().add(novaComida.getNome());
            comandaRepository.save(comanda);

        return new PedidoResponseDTO(pedidoRepository.save(pedido));
}


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
    public Object registrarPagamento(Integer mesa, BigDecimal valorRecebido,Long cartaoId, FormaPagamento formaDePagamento, Integer qtdPessoas, String senhaCartao) {
        Pedido pedido = buscarPorMesa(mesa);

        if (qtdPessoas == null || qtdPessoas <= 0) {qtdPessoas = 1;}
        BandeiraCartao bandeiraDoCartao = null;

        if (formaDePagamento != FormaPagamento.PIX && formaDePagamento != FormaPagamento.DINHEIRO) {
            if (cartaoId == null) {
                throw new RuntimeException("ID do cartão é obrigatório para esta forma de pagamento.");
            }
            CartaoCliente cartaoUsado = cartaoClienteRepository.findById(cartaoId)
                    .orElseThrow(() -> new RuntimeException("Cartão não encontrado."));

            if (!cartaoUsado.getSenha().equals(senhaCartao)) {
                throw new RuntimeException("Senha do cartão inválida!");
            }
            if(!cartaoUsado.getFormaPagamento().equals(formaDePagamento)){
                throw new RuntimeException("Forma de pagamento inválida!");
            }
            if (valorRecebido.compareTo(cartaoUsado.getSaldo()) > 0) {
                throw new RuntimeException("Saldo insuficiente!");
            }
            bandeiraDoCartao = cartaoUsado.getBandeiraCartao();
        }
        pedido.setValorPago(pedido.getValorPago().add(valorRecebido));

        BigDecimal subtotalSemTaxa = pedido.getSubtotal();
        BigDecimal totalPago = pedido.getValorPago();

        if (totalPago.compareTo(subtotalSemTaxa) >= 0) {
            pedido.setStatusPagamento(true);
            Recibo recibo = new Recibo(pedido, formaDePagamento);
            recibo.setBandeiraCartao(bandeiraDoCartao);
            BigDecimal media = totalPago.divide(BigDecimal.valueOf(qtdPessoas), 2, RoundingMode.HALF_UP);
            recibo.setMedia(media);
            recibo.setQtdDePessoas(qtdPessoas);

            reciboRepository.save(recibo);
            pedido.getComidas().clear();
            pedidoRepository.delete(pedido);

            return new ReciboResponseDTO(recibo);
        }
        return new PedidoResponseDTO(pedidoRepository.save(pedido));
    }

    @Transactional
    public void cancelarComida(Integer mesa, String comidaNome){
        Pedido pedido = buscarPorMesa(mesa);
        Comida comidaCancelada = comidaService.findComidaByName(comidaNome);
        pedido.getComidas().remove(comidaCancelada);
    }
}
