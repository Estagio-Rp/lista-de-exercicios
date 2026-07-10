package br.com.rpinfo.analuisa.application.port.input;

public interface AutenticarUsuarioInputPort {

    String autenticar(
            String email,
            String senha
    );
}