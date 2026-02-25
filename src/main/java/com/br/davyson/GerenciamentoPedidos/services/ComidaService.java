package com.br.davyson.GerenciamentoPedidos.services;

import com.br.davyson.GerenciamentoPedidos.dto.ComidaRequestDTO;
import com.br.davyson.GerenciamentoPedidos.entitys.Categoria;
import com.br.davyson.GerenciamentoPedidos.entitys.Comida;
import com.br.davyson.GerenciamentoPedidos.exceptions.ObjectNotFoundException;
import com.br.davyson.GerenciamentoPedidos.repositorys.CategoriaRepository;
import com.br.davyson.GerenciamentoPedidos.repositorys.ComidaRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComidaService {
    private final ComidaRepository comidaRepository;
    private final CategoriaRepository categoriaRepository;

    public ComidaService(ComidaRepository comidaRepository, CategoriaRepository categoriaRepository) {
        this.comidaRepository = comidaRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<Comida> openMenu(){
        return comidaRepository.findAll();
    }

    public List<Comida> findComida(String nome) {
        List<Comida> resultados = comidaRepository.findByNomeContainingIgnoreCase(nome);
        if (resultados.isEmpty()) {
            throw new ObjectNotFoundException("Nenhuma comida encontrada com o termo: " + nome);
        }
        return resultados;
    }
    public Comida findById(Long id) {
        return comidaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Comida não encontrada com ID: " + id));
    }
    public List<Comida> findComidaByCategoria(String nome){
        List<Comida> foodList = comidaRepository.findByCategoriaNomeIgnoreCase(nome);
        if(foodList.isEmpty()){
            throw new ObjectNotFoundException("Categoria sem comidas cadastradas");
        }
        return foodList;
    }
    @Transactional
    public Comida saveFood(Comida comida, String categoriaNome) {
        Categoria categoriaExistente = categoriaRepository.findByNomeIgnoreCase(categoriaNome)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Não foi possível cadastrar: A categoria '" + categoriaNome + "' não foi encontrada!"));
        if (comidaRepository.existsByNomeIgnoreCase(comida.getNome())) {
            throw new DataIntegrityViolationException("A comida '" + comida.getNome() + "' já existe!");
        }
        comida.setCategoria(categoriaExistente);
        return comidaRepository.save(comida);
    }

    @Transactional
    public Comida updateFood(String nome, ComidaRequestDTO comidaAtualizada) {
        Comida comidaExistente = comidaRepository.findByNomeIgnoreCase(nome)
                .orElseThrow(() -> new ObjectNotFoundException("Comida '" + nome + "' não encontrada para atualização."));

        if (comidaAtualizada.nome() != null) comidaExistente.setNome(comidaAtualizada.nome());
        if (comidaAtualizada.descricao() != null) comidaExistente.setDescricao(comidaAtualizada.descricao());
        if (comidaAtualizada.preco() != null) comidaExistente.setPreco(comidaAtualizada.preco());

        return comidaRepository.save(comidaExistente);
    }
    @Transactional
    public void deleteFood(String nome) {
        Comida comida = comidaRepository.findByNomeIgnoreCase(nome)
                .orElseThrow(() -> new ObjectNotFoundException("Não foi possível deletar: Comida '" + nome + "' não encontrada."));

        comidaRepository.delete(comida);
    }
}
