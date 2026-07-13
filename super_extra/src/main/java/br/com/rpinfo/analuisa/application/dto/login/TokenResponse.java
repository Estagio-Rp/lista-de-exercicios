package br.com.rpinfo.analuisa.application.dto.login;

public record TokenResponse(
        String token,
        String tipo,
        long expiresIn
) {
}