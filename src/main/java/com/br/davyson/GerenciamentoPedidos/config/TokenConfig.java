package com.br.davyson.GerenciamentoPedidos.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.br.davyson.GerenciamentoPedidos.entitys.Atendente;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class TokenConfig {
    private String secret = "secret";

    public String generateToken(Atendente atendente){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withClaim("AtendenteId", atendente.getId())
                .withClaim("role", atendente.getRole().name())
                .withSubject(atendente.getEmail())
                .withExpiresAt(Instant.now().plusSeconds(84600))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }

    public Optional<JWTUserData> validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decoded = JWT.require(algorithm).build().verify(token);

            return Optional.of(JWTUserData.builder()
                    .userId(decoded.getClaim("userId").asLong())
                    .email(decoded.getSubject())
                    .role(decoded.getClaim("role").asString())
                    .build());
        } catch (JWTVerificationException jwtEx){
            return Optional.empty();
        }
    }

}
