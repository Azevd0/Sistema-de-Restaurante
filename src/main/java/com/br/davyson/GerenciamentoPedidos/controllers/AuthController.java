package com.br.davyson.GerenciamentoPedidos.controllers;

import com.br.davyson.GerenciamentoPedidos.config.TokenConfig;
import com.br.davyson.GerenciamentoPedidos.dto.request.AtendenteRegisterRequest;
import com.br.davyson.GerenciamentoPedidos.dto.request.LoginAtendenteRequest;
import com.br.davyson.GerenciamentoPedidos.dto.response.AtendenteRegisterResponse;
import com.br.davyson.GerenciamentoPedidos.dto.response.LoginAtendenteResponse;
import com.br.davyson.GerenciamentoPedidos.entitys.Atendente;
import com.br.davyson.GerenciamentoPedidos.enums.Role;
import com.br.davyson.GerenciamentoPedidos.services.AtendenteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AtendenteService atendenteService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenConfig tokenConfig;

    public AuthController(AtendenteService atendenteService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, TokenConfig tokenConfig) {
        this.atendenteService = atendenteService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenConfig = tokenConfig;
    }

    @Operation(summary = "Autenticar usuário")
    @PostMapping("/login")
    public ResponseEntity<LoginAtendenteResponse> login(@Valid @RequestBody LoginAtendenteRequest request){
        UsernamePasswordAuthenticationToken userAndPwd = new UsernamePasswordAuthenticationToken(request.email(), request.senha());
        Authentication auth = authenticationManager.authenticate(userAndPwd);
        Atendente atendenteAutenticado = (Atendente) auth.getPrincipal();
        String token = tokenConfig.generateToken(atendenteAutenticado);
        return ResponseEntity.ok().body(new LoginAtendenteResponse(token));
    }

    @Operation(summary = "Registrar usuário")
    @PostMapping("/registro")
    public ResponseEntity<AtendenteRegisterResponse> cadastrar(@RequestBody @Valid AtendenteRegisterRequest dto, Role cargo) {
        Atendente atendente = new Atendente();
        atendente.setNome(dto.nome());
        atendente.setEmail(dto.email());
        atendente.setSenha(passwordEncoder.encode(dto.senha()));
        atendente.setRole(cargo);

        AtendenteRegisterResponse salvo = atendenteService.saveAtendente(atendente);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }
}
