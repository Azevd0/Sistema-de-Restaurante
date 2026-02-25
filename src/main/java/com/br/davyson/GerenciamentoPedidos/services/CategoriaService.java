package com.br.davyson.GerenciamentoPedidos.services;

import com.br.davyson.GerenciamentoPedidos.entitys.Categoria;
import com.br.davyson.GerenciamentoPedidos.exceptions.ObjectNotFoundException;
import com.br.davyson.GerenciamentoPedidos.repositorys.CategoriaRepository;
import com.br.davyson.GerenciamentoPedidos.repositorys.ComidaRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final  ComidaRepository comidaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, ComidaRepository comidaRepository) {
        this.categoriaRepository = categoriaRepository;
        this.comidaRepository = comidaRepository;
    }

    public List<Categoria> ListAll() {
        return categoriaRepository.findAll();
    }

    public Categoria findById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada! ID: " + id));
    }

    public Categoria findByNome(String nome) {
        return categoriaRepository.findByNomeIgnoreCase(nome)
                .orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada! Nome: " + nome));
    }

    @Transactional
    public Categoria save(Categoria categoria) {
        if (categoriaRepository.existsByNomeIgnoreCase(categoria.getNome())) {
            throw new DataIntegrityViolationException("A categoria '" + categoria.getNome() + "' já existe!");
        }
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria updateByNome(String nome, Categoria novoNome) {
        Categoria categoria = categoriaRepository.findByNomeIgnoreCase(nome)
                .orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada: " + nome));
        categoria.setNome(novoNome.getNome());

        return categoriaRepository.save(categoria);
    }

    @Transactional
    public void deleteByNome(String nome) {
        Categoria categoria = categoriaRepository.findByNomeIgnoreCase(nome)
                .orElseThrow(() -> new ObjectNotFoundException("Categoria '" + nome + "' não encontrada."));

        if (comidaRepository.existsByCategoria(categoria)) {
            throw new DataIntegrityViolationException("Não é possível deletar a categoria '" + nome + "' porque ela possui comidas vinculadas no cardápio.");
        }
        categoriaRepository.delete(categoria);
    }
}

