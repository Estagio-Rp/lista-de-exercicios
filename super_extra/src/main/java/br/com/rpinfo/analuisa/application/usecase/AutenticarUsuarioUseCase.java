package br.com.rpinfo.analuisa.application.usecase;

import br.com.rpinfo.analuisa.application.port.input.AutenticarUsuarioInputPort;
import br.com.rpinfo.analuisa.application.port.output.PasswordEncoderPort;
import br.com.rpinfo.analuisa.application.port.output.TokenGeneratorPort;
import br.com.rpinfo.analuisa.application.port.output.UsuarioRepositoryPort;
import br.com.rpinfo.analuisa.domain.exceptions.CredenciaisInvalidasException;
import br.com.rpinfo.analuisa.domain.model.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class AutenticarUsuarioUseCase implements AutenticarUsuarioInputPort {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final TokenGeneratorPort tokenGeneratorPort;

    public AutenticarUsuarioUseCase(
            UsuarioRepositoryPort usuarioRepositoryPort,
            PasswordEncoderPort passwordEncoderPort,
            TokenGeneratorPort tokenGeneratorPort
    ) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.tokenGeneratorPort = tokenGeneratorPort;
    }

    @Override
    public String autenticar(
            String email,
            String senha
    ) {
        if (email == null || email.isBlank()) {
            throw new CredenciaisInvalidasException();
        }

        if (senha == null || senha.isBlank()) {
            throw new CredenciaisInvalidasException();
        }

        String emailNormalizado = email.trim().toLowerCase();

        Usuario usuario = usuarioRepositoryPort
                .buscarPorEmail(emailNormalizado)
                .orElseThrow(CredenciaisInvalidasException::new);

        boolean senhaCorresponde = passwordEncoderPort.corresponde(
                senha,
                usuario.getSenha()
        );

        if (!senhaCorresponde) {
            throw new CredenciaisInvalidasException();
        }

        return tokenGeneratorPort.gerarToken(usuario);
    }
}