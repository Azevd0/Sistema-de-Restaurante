package com.br.davyson.GerenciamentoPedidos.repositorys;

import com.br.davyson.GerenciamentoPedidos.entitys.Atendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AtendenteRepository extends JpaRepository<Atendente, Long> {

    boolean existsByNomeIgnoreCase(String nome);
    Optional<Atendente> findByNomeIgnoreCase(String nome);
    Optional<UserDetails> findByEmail(String email);
    Optional<Atendente> findAtendenteByEmail(String email);
}
