package com.br.davyson.GerenciamentoPedidos.dto.response;

public class LoginAtendenteResponse {
    private String token;

    public LoginAtendenteResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
