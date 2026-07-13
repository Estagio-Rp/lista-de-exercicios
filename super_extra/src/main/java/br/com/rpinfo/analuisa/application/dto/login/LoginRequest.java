package br.com.rpinfo.analuisa.application.dto.login;

public record LoginRequest(
        String email,
        String senha
) {
}