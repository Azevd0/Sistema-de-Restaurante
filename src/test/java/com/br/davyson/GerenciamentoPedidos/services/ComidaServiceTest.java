package com.br.davyson.GerenciamentoPedidos.services;

import com.br.davyson.GerenciamentoPedidos.dto.request.ComidaRequestDTO;
import com.br.davyson.GerenciamentoPedidos.dto.response.ComidaResponseDTO;
import com.br.davyson.GerenciamentoPedidos.entitys.Categoria;
import com.br.davyson.GerenciamentoPedidos.entitys.Comida;
import com.br.davyson.GerenciamentoPedidos.exceptions.ObjectNotFoundException;
import com.br.davyson.GerenciamentoPedidos.repositorys.CategoriaRepository;
import com.br.davyson.GerenciamentoPedidos.repositorys.ComidaRepository;
import com.br.davyson.GerenciamentoPedidos.wrapper.ListWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
class ComidaServiceTest {

    private ComidaRepository comidaRepository;
    private CategoriaRepository categoriaRepository;
    private ComidaService comidaService;

    @BeforeEach
    void setUp() {
        comidaRepository = Mockito.mock(ComidaRepository.class);
        categoriaRepository = Mockito.mock(CategoriaRepository.class);
        comidaService = new ComidaService(comidaRepository, categoriaRepository);
    }

    @Test
    @DisplayName("Registrar comida")
    void deveSalvarERetornarQuandoValido() {
        ComidaRequestDTO requestDTO = new ComidaRequestDTO("Pizza", "Pizza de queijo", BigDecimal.valueOf(40.0));
        Categoria categoria = new Categoria();
        categoria.setNome("Pizzas");

        Mockito.when(categoriaRepository.findByNomeIgnoreCase("Pizzas")).thenReturn(Optional.of(categoria));
        Mockito.when(comidaRepository.existsByNomeIgnoreCase("Pizza")).thenReturn(false);

        Comida comidaSalva = new Comida();
        comidaSalva.setNome("Pizza");
        comidaSalva.setCategoria(categoria);
        Mockito.when(comidaRepository.save(Mockito.any(Comida.class))).thenReturn(comidaSalva);

        ComidaResponseDTO response = comidaService.saveFood(requestDTO, "Pizzas");

        assertNotNull(response);
        assertEquals("Pizza", response.getNome());
        Mockito.verify(comidaRepository, Mockito.times(1)).save(Mockito.any(Comida.class));
    }

    @Test
    @DisplayName("Lançar exceção se categoria não existir")
    void deveLancarExcecaoQuandoCategoriaNaoExiste() {
        ComidaRequestDTO requestDTO = new ComidaRequestDTO("Pizza", "Pizza de queijo", BigDecimal.valueOf(40.0));

        Mockito.when(categoriaRepository.findByNomeIgnoreCase("Doces")).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> comidaService.saveFood(requestDTO, "Doces"));
        Mockito.verify(comidaRepository, Mockito.never()).save(Mockito.any(Comida.class));
    }

    @Test
    @DisplayName("Lançar exceção se a comida já exisir")
    void deveLancarExcecaoQuandoComidaJaExiste() {
        ComidaRequestDTO requestDTO = new ComidaRequestDTO("Pizza", "Pizza de queijo", BigDecimal.valueOf(40.0));
        Categoria categoria = new Categoria();
        categoria.setNome("Massas");

        Mockito.when(categoriaRepository.findByNomeIgnoreCase("Massas")).thenReturn(Optional.of(categoria));
        Mockito.when(comidaRepository.existsByNomeIgnoreCase("Pizza")).thenReturn(true);

        assertThrows(DataIntegrityViolationException.class, () -> comidaService.saveFood(requestDTO, "Massas"));
    }

    @Test
    @DisplayName("Retornar lista de comidas")
    void deveRetornarListaDeComidas() {
        Comida comida = new Comida();
        comida.setNome("Bolo de Chocolate");
        comida.setCategoria(new Categoria());

        Mockito.when(comidaRepository.findByNomeContainingIgnoreCase("Bolo")).thenReturn(List.of(comida));

        ListWrapper<ComidaResponseDTO> response = comidaService.findComida("Bolo");

        assertFalse(response.getContent().isEmpty());
        assertEquals("Bolo de Chocolate", response.getContent().get(0).getNome());
    }
}