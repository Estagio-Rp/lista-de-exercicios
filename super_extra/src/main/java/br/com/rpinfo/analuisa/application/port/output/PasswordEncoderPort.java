package br.com.rpinfo.analuisa.application.port.output;

public interface PasswordEncoderPort {

    String codificar(String senha);

    boolean corresponde(
            String senhaInformada,
            String senhaCodificada
    );
}