package br.com.rpinfo.analuisa.adapter.security;

import br.com.rpinfo.analuisa.application.port.output.PasswordEncoderPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoderAdapter
        implements PasswordEncoderPort {

    private final BCryptPasswordEncoder passwordEncoder;

    public BCryptPasswordEncoderAdapter() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String codificar(String senha) {
        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException(
                    "A senha não pode ser vazia."
            );
        }

        return passwordEncoder.encode(senha);
    }

    @Override
    public boolean corresponde(
            String senhaInformada,
            String senhaCodificada
    ) {
        if (senhaInformada == null ||
                senhaInformada.isBlank() ||
                senhaCodificada == null ||
                senhaCodificada.isBlank()) {

            return false;
        }

        return passwordEncoder.matches(
                senhaInformada,
                senhaCodificada
        );
    }
}