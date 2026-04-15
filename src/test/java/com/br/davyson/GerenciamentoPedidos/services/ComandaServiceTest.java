package com.br.davyson.GerenciamentoPedidos.services;

import com.br.davyson.GerenciamentoPedidos.dto.response.ComandaDTO;
import com.br.davyson.GerenciamentoPedidos.entitys.Comanda;
import com.br.davyson.GerenciamentoPedidos.enums.Periodo;
import com.br.davyson.GerenciamentoPedidos.repositorys.ComandaRepository;
import com.br.davyson.GerenciamentoPedidos.wrapper.ListWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class ComandaServiceTest {

    private ComandaRepository comandaRepository;
    private ComandaService comandaService;

    @BeforeEach
    void setUp() {
        comandaRepository = Mockito.mock(ComandaRepository.class);
        comandaService = new ComandaService(comandaRepository);
    }
    @Test
    @DisplayName("Retornar lista de comandas por período")
    void deveRetornarComandasDoPeriodo() {
        Comanda comanda = new Comanda();
        comanda.setMesa(5);
        comanda.setAtendenteNome("Maria");

        Mockito.when(comandaRepository.findByDataLancamentoAfter(Mockito.any(LocalDateTime.class)))
                .thenReturn(List.of(comanda));

        ListWrapper<ComandaDTO> response = comandaService.listarHistorico(Periodo.HOJE);

        assertNotNull(response);
        assertFalse(response.getContent().isEmpty());
        assertEquals(5, response.getContent().get(0).getMesa());
        Mockito.verify(comandaRepository, Mockito.times(1)).findByDataLancamentoAfter(Mockito.any(LocalDateTime.class));
    }
}