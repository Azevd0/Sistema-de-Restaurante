package com.br.davyson.GerenciamentoPedidos.services;

import com.br.davyson.GerenciamentoPedidos.config.JWTUserData;
import com.br.davyson.GerenciamentoPedidos.dto.request.PedidoRequestDTO;
import com.br.davyson.GerenciamentoPedidos.dto.response.PedidoResponseDTO;
import com.br.davyson.GerenciamentoPedidos.dto.response.ReciboResponseDTO;
import com.br.davyson.GerenciamentoPedidos.entitys.*;
import com.br.davyson.GerenciamentoPedidos.enums.BandeiraCartao;
import com.br.davyson.GerenciamentoPedidos.enums.FormaPagamento;
import com.br.davyson.GerenciamentoPedidos.exceptions.ObjectNotFoundException;
import com.br.davyson.GerenciamentoPedidos.repositorys.*;
import com.br.davyson.GerenciamentoPedidos.wrapper.ListWrapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final ComidaService comidaService;
    private final ComandaRepository comandaRepository;
    private final CartaoClienteRepository cartaoClienteRepository;
    private final AtendenteService atendenteService;

    public PedidoService(PedidoRepository pedidoRepository, ComidaRepository comidaRepository, ReciboRepository reciboRepository, ComidaService comidaService, ComandaRepository comandaRepository, CartaoClienteRepository cartaoClienteRepository, AtendenteService atendenteService) {
        this.pedidoRepository = pedidoRepository;
        this.comidaRepository = comidaRepository;
        this.reciboRepository = reciboRepository;
        this.comidaService = comidaService;
        this.comandaRepository = comandaRepository;
        this.cartaoClienteRepository = cartaoClienteRepository;
        this.atendenteService = atendenteService;
    }

    @Transactional
    @CacheEvict(value = "pedidos", allEntries = true)
    public PedidoResponseDTO lancarPedido(PedidoRequestDTO dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JWTUserData usuarioAutenticado = (JWTUserData) authentication.getPrincipal();
        Atendente atendente = atendenteService.buscarEntidadePorEmail(usuarioAutenticado.email());

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

        Pedido pedidoSalvo = pedidoRepository.save(novoPedido);

            Comanda comanda = new Comanda();
            comanda.setMesa(pedidoSalvo.getMesa());
            comanda.setAtendenteNome(atendente.getNome());
            comanda.setComidaNome(comidas.stream().map(Comida::getNome).toList());
            comanda.setObservacao(dto.observacao());
            comandaRepository.save(comanda);


        return new PedidoResponseDTO(pedidoSalvo);
    }
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "pedidos", key = "#mesa"),
            @CacheEvict(value = "pedidos", key = "'pendentes'")
    })
    public PedidoResponseDTO adicionarComida(Integer mesa, String comidaNome, String observacao) {
        Pedido pedido = buscarEntidadePorMesa(mesa);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JWTUserData usuarioAutenticado = (JWTUserData) authentication.getPrincipal();
        Atendente atendente = atendenteService.buscarEntidadePorEmail(usuarioAutenticado.email());

            Comida novaComida = comidaRepository.findByNomeIgnoreCase(comidaNome)
                    .orElseThrow(() -> new ObjectNotFoundException("Comida '" + comidaNome + "' não encontrada no cardápio."));

            pedido.getComidas().add(novaComida);
            Comanda comanda = new Comanda();
            comanda.setMesa(pedido.getMesa());
            comanda.setAtendenteNome(atendente.getNome());
            comanda.getComidaNome().add(novaComida.getNome());
            comanda.setObservacao(observacao);
            comandaRepository.save(comanda);

        return new PedidoResponseDTO(pedidoRepository.save(pedido));
}

    public Pedido buscarEntidadePorMesa(Integer mesa) {
        return pedidoRepository.findByMesa(mesa)
                .orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado para a mesa " + mesa));
    }

    @Cacheable(value = "pedidos", key = "#mesa")
    public PedidoResponseDTO buscarDTOPorMesa(Integer mesa) {
        return pedidoRepository.findByMesa(mesa)
                .map(PedidoResponseDTO::new)
                .orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado para a mesa " + mesa));
    }

    @Cacheable(value = "pedidos", key = "'pendentes'")
    public ListWrapper<PedidoResponseDTO> buscarPedidosPendentes(){
        List<PedidoResponseDTO> pedidos = pedidoRepository.findAll().stream()
                .map(PedidoResponseDTO::new)
                .toList();
        return new ListWrapper<>(pedidos);
    }
    @Transactional
    @CacheEvict(value = "pedidos", allEntries = true)
    public PedidoResponseDTO transferirComida(Integer mesaOrigem, Integer mesaDestino, String nomeComida) {
        Pedido pedidoOrigem = buscarEntidadePorMesa(mesaOrigem);
        Pedido pedidoDestino = buscarEntidadePorMesa(mesaDestino);

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
    @CacheEvict(value = "pedidos", allEntries = true)
    public PedidoResponseDTO alterarMesa(Integer mesaAtual, Integer novaMesa) {
        Pedido pedido = buscarEntidadePorMesa(mesaAtual);
        pedido.setMesa(novaMesa);
        return new PedidoResponseDTO(pedidoRepository.save(pedido));
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "historico_financeiro", allEntries = true),
            @CacheEvict(value = "pedidos", allEntries = true)
    })
    public Object registrarPagamento(Integer mesa, BigDecimal valorRecebido,Long cartaoId, FormaPagamento formaDePagamento, Integer qtdPessoas, String senhaCartao) {
        Pedido pedido = buscarEntidadePorMesa(mesa);

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
    @CacheEvict(value = "pedidos", allEntries = true)
    public void cancelarComida(Integer mesa, String comidaNome){
        Pedido pedido = buscarEntidadePorMesa(mesa);
        Comida comidaCancelada = comidaService.findComidaByName(comidaNome);
        pedido.getComidas().remove(comidaCancelada);
        pedidoRepository.save(pedido);
    }
}
