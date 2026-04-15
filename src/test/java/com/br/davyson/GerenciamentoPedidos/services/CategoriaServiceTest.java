package com.br.davyson.GerenciamentoPedidos.services;

import com.br.davyson.GerenciamentoPedidos.entitys.Categoria;
import com.br.davyson.GerenciamentoPedidos.repositorys.CategoriaRepository;
import com.br.davyson.GerenciamentoPedidos.repositorys.ComidaRepository;
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
class CategoriaServiceTest {
    private CategoriaRepository categoriaRepository;
    private ComidaRepository comidaRepository;
    private CategoriaService categoriaService;

    @BeforeEach
    void setUp() {
        categoriaRepository = Mockito.mock(CategoriaRepository.class);
        comidaRepository = Mockito.mock(ComidaRepository.class);
        categoriaService = new CategoriaService(categoriaRepository, comidaRepository);
    }

    @Test
    @DisplayName("Deletar categoria sem comidas")
    void deveDeletarCategoriaSeNaoPossuirComidasVinculadas() {
        Categoria categoria = new Categoria();
        categoria.setNome("Bebidas");

        Mockito.when(categoriaRepository.findByNomeIgnoreCase("Bebidas")).thenReturn(Optional.of(categoria));
        Mockito.when(comidaRepository.existsByCategoria(categoria)).thenReturn(false);

        assertDoesNotThrow(() -> categoriaService.deleteByNome("Bebidas"));
        Mockito.verify(categoriaRepository, Mockito.times(1)).delete(categoria);
    }

    @Test
    @DisplayName("Lançar exceção se a categoria tiver comidas")
    void deveLancarExcecaoQuandoPossuirComidasVinculadas() {
        Categoria categoria = new Categoria();
        categoria.setNome("Bebidas");

        Mockito.when(categoriaRepository.findByNomeIgnoreCase("Bebidas")).thenReturn(Optional.of(categoria));
        Mockito.when(comidaRepository.existsByCategoria(categoria)).thenReturn(true);

        assertThrows(DataIntegrityViolationException.class, () -> categoriaService.deleteByNome("Bebidas"));
        Mockito.verify(categoriaRepository, Mockito.never()).delete(Mockito.any(Categoria.class));
    }
}