package com.br.davyson.GerenciamentoPedidos.services;

import com.br.davyson.GerenciamentoPedidos.entitys.Atendente;
import com.br.davyson.GerenciamentoPedidos.exceptions.ObjectNotFoundException;
import com.br.davyson.GerenciamentoPedidos.repositorys.AtendenteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
class AtendenteServiceTest {
    private AtendenteRepository atendenteRepository;
    private AtendenteService atendenteService;

    @BeforeEach
    void setUp() {
        atendenteRepository = Mockito.mock(AtendenteRepository.class);
        atendenteService = new AtendenteService(atendenteRepository);
    }

    @Test
    @DisplayName("Encontrar entidade Atendente por nome")
    void buscarEntidadePorNome() {
        Atendente atendente = new Atendente();
        atendente.setNome("João");
        Mockito.when(atendenteRepository.findByNomeIgnoreCase("João")).thenReturn(Optional.of(atendente));
        Atendente resultado = atendenteService.buscarEntidadePorNome("João");
        assertNotNull(resultado);
        assertEquals("João", resultado.getNome());
        Mockito.verify(atendenteRepository, Mockito.times(1)).findByNomeIgnoreCase("João");
    }

    @Test
    @DisplayName("Retornar exceção de atendente não encontrado")
    void deveLancarExcecaoQuandoNaoEncontrado() {
        Mockito.when(atendenteRepository.findByNomeIgnoreCase("Maria")).thenReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class, () -> atendenteService.buscarEntidadePorNome("Maria"));
    }

    @Test
    @DisplayName("Lançar exceção quando nome já existir")
    void deveLancarExcecaoQuandoNomeJaExiste() {
        Atendente atendente = new Atendente();
        atendente.setNome("Carlos");

        Mockito.when(atendenteRepository.existsByNomeIgnoreCase("Carlos")).thenReturn(true);

        assertThrows(DataIntegrityViolationException.class, () -> atendenteService.saveAtendente(atendente));
        Mockito.verify(atendenteRepository, Mockito.never()).save(Mockito.any(Atendente.class));
    }
}