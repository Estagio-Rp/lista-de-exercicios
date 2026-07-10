package br.com.rpinfo.analuisa.domain.exceptions;

public class EmailJaCadastradoException extends RuntimeException {

    public EmailJaCadastradoException() {
        super("Já existe um usuário cadastrado com este e-mail.");
    }

    public EmailJaCadastradoException(String email) {
        super("Já existe um usuário cadastrado com o e-mail: " + email);
    }
}