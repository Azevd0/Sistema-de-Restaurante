package com.br.davyson.GerenciamentoPedidos.services;

import com.br.davyson.GerenciamentoPedidos.dto.response.CartaoClienteResponseDTO;
import com.br.davyson.GerenciamentoPedidos.entitys.CartaoCliente;
import com.br.davyson.GerenciamentoPedidos.exceptions.ObjectNotFoundException;
import com.br.davyson.GerenciamentoPedidos.repositorys.CartaoClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
class CartaoClienteServiceTest {

    private CartaoClienteRepository cartaoClienteRepository;
    private CartaoClienteService cartaoClienteService;

    @BeforeEach
    void setUp() {
        cartaoClienteRepository = Mockito.mock(CartaoClienteRepository.class);
        cartaoClienteService = new CartaoClienteService(cartaoClienteRepository);
    }

    @Test
    @DisplayName("Retornar cartão caso o encontre")
    void deveRetornarCartaoQuandoEncontrado() {
        CartaoCliente cartao = new CartaoCliente();
        cartao.setId(1L);

        Mockito.when(cartaoClienteRepository.findById(1L)).thenReturn(Optional.of(cartao));
        CartaoClienteResponseDTO response = cartaoClienteService.buscarPorId(1L);
        assertNotNull(response);
        Mockito.verify(cartaoClienteRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Lançar exceção caso não encontre")
    void deveLancarExcecaoQuandoNaoEncontrado() {
        Mockito.when(cartaoClienteRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class, () -> cartaoClienteService.buscarPorId(99L));
    }
}