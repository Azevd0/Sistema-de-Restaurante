package com.br.davyson.GerenciamentoPedidos.config;

import com.br.davyson.GerenciamentoPedidos.repositorys.AtendenteRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthConfig implements UserDetailsService {

    private final AtendenteRepository atendenteRepository;

    public AuthConfig(AtendenteRepository atendenteRepository) {
        this.atendenteRepository = atendenteRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return atendenteRepository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("Atendente não encontrado"));
    }
}
