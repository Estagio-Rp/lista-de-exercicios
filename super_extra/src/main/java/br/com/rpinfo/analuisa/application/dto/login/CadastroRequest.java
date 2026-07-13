package br.com.rpinfo.analuisa.application.dto.login;

public record CadastroRequest(
        String nome,
        String email,
        String senha
) {
}