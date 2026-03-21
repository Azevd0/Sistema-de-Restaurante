package com.br.davyson.GerenciamentoPedidos.services;

import com.br.davyson.GerenciamentoPedidos.dto.CartaoClienteResponseDTO;
import com.br.davyson.GerenciamentoPedidos.entitys.CartaoCliente;
import com.br.davyson.GerenciamentoPedidos.exceptions.ObjectNotFoundException;
import com.br.davyson.GerenciamentoPedidos.repositorys.CartaoClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartaoClienteService {
    private final CartaoClienteRepository repository;

    public CartaoClienteService(CartaoClienteRepository repository) {
        this.repository = repository;
    }

    public List<CartaoClienteResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(CartaoClienteResponseDTO::new)
                .toList();
    }

    public CartaoClienteResponseDTO buscarPorId(Long id) {
        CartaoCliente cartao = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cartão não encontrado com o ID: " + id));
        return new CartaoClienteResponseDTO(cartao);
    }
}
