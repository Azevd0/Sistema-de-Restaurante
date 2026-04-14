package com.br.davyson.GerenciamentoPedidos.dto.request;

import jakarta.validation.constraints.Size;

public record AtendenteRegisterRequest(String nome, String email,
                                       @Size(min = 5, max = 15, message = "A senha deve ter entre 5 e 15 caracteres")
                                       String senha){}
