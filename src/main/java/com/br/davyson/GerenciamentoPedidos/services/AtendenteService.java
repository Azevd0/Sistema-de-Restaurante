package com.br.davyson.GerenciamentoPedidos.services;

import com.br.davyson.GerenciamentoPedidos.dto.request.AtendenteRegisterRequest;
import com.br.davyson.GerenciamentoPedidos.dto.response.AtendenteRegisterResponse;
import com.br.davyson.GerenciamentoPedidos.dto.response.PedidoResponseDTO;
import com.br.davyson.GerenciamentoPedidos.entitys.Atendente;
import com.br.davyson.GerenciamentoPedidos.exceptions.ObjectNotFoundException;
import com.br.davyson.GerenciamentoPedidos.repositorys.AtendenteRepository;
import com.br.davyson.GerenciamentoPedidos.wrapper.ListWrapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AtendenteService {
    private final AtendenteRepository atendenteRepository;


    public AtendenteService(AtendenteRepository atendenteRepository) {
        this.atendenteRepository = atendenteRepository;
    }

    public Atendente buscarEntidadePorNome(String name){
        return atendenteRepository.findByNomeIgnoreCase(name)
                .orElseThrow(() -> new ObjectNotFoundException("Atendente não encontrado."));
    }
    public Atendente buscarEntidadePorEmail(String email){
        return atendenteRepository.findAtendenteByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("Usuario não encontrado"));
    }
    @Cacheable(value = "user", key = "'atendente_' + #name")
    public AtendenteRegisterResponse buscarPorNome(String name){
        Atendente atendente = atendenteRepository.findByNomeIgnoreCase(name)
                .orElseThrow(() -> new ObjectNotFoundException("Atendente não encontrado."));
        return new AtendenteRegisterResponse(atendente);
    }

    public Atendente findById(Long id){
        return atendenteRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Atendente não encontrado com Id "+ id));
    }
    @Cacheable(value = "atendentes", key = "'pedidos_atendente_' + #atendente.id")
    public ListWrapper<PedidoResponseDTO> listarPedidosDoAtendente(Atendente atendente){
        List<PedidoResponseDTO> atendentePedidos = atendente.getPedidos().stream().map(PedidoResponseDTO::new).toList();
        return new ListWrapper<>(atendentePedidos);
    }
    @Transactional
    @CacheEvict(value = "user", allEntries = true)
    public AtendenteRegisterResponse saveAtendente(Atendente atendente){
        if (atendenteRepository.existsByNomeIgnoreCase(atendente.getNome())) {
            throw new DataIntegrityViolationException("Já existe um atendente com esse nome!");
        }
        atendenteRepository.save(atendente);
        return new AtendenteRegisterResponse(atendente);
    }

    @Transactional
    @CacheEvict(value = "user", allEntries = true)
    public AtendenteRegisterResponse updateAtendenteByName(String name, AtendenteRegisterRequest dto) {
        Atendente atendenteExistente = buscarEntidadePorNome(name);

        if (dto.nome() != null) atendenteExistente.setNome(dto.nome());
        if (dto.senha() != null) atendenteExistente.setSenha(dto.senha());

        atendenteRepository.save(atendenteExistente);
        return new AtendenteRegisterResponse(atendenteExistente);
    }

    @Transactional
    @CacheEvict(value = "user", allEntries = true)
    public void deleteUser(String name) {
        Atendente atendente = buscarEntidadePorNome(name);
        atendenteRepository.delete(atendente);
    }
}
