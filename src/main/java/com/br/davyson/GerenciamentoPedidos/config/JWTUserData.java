package com.br.davyson.GerenciamentoPedidos.config;

import lombok.Builder;

@Builder
public record JWTUserData(Long userId, String email, String role) {
}
