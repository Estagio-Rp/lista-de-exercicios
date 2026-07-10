package br.com.rpinfo.analuisa.application.usecase;

import br.com.rpinfo.analuisa.application.port.input.CadastrarUsuarioInputPort;
import br.com.rpinfo.analuisa.application.port.output.PasswordEncoderPort;
import br.com.rpinfo.analuisa.application.port.output.UsuarioRepositoryPort;
import br.com.rpinfo.analuisa.domain.exceptions.CampoInvalidoException;
import br.com.rpinfo.analuisa.domain.exceptions.EmailJaCadastradoException;
import br.com.rpinfo.analuisa.domain.model.entity.Perfil;
import br.com.rpinfo.analuisa.domain.model.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class CadastrarUsuarioUseCase implements CadastrarUsuarioInputPort {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;

    public CadastrarUsuarioUseCase(
            UsuarioRepositoryPort usuarioRepositoryPort,
            PasswordEncoderPort passwordEncoderPort
    ) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public Usuario cadastrar(
            String nome,
            String email,
            String senha,
            Perfil perfil
    ) {
        validarDados(nome, email, senha);

        String nomeNormalizado = nome.trim();
        String emailNormalizado = email.trim().toLowerCase();

        if (usuarioRepositoryPort.existePorEmail(emailNormalizado)) {
            throw new EmailJaCadastradoException(emailNormalizado);
        }

        Perfil perfilDefinido = perfil != null
                ? perfil
                : Perfil.CLIENTE;

        String senhaCodificada = passwordEncoderPort.codificar(senha);

        Usuario usuario = new Usuario(
                nomeNormalizado,
                emailNormalizado,
                senhaCodificada,
                perfilDefinido
        );

        return usuarioRepositoryPort.salvar(usuario);
    }

    private void validarDados(
            String nome,
            String email,
            String senha
    ) {
        if (nome == null || nome.isBlank()) {
            throw new CampoInvalidoException(
                    "O nome do usuário é obrigatório."
            );
        }

        if (nome.trim().length() < 3) {
            throw new CampoInvalidoException(
                    "O nome do usuário deve possuir pelo menos 3 caracteres."
            );
        }

        if (nome.trim().length() > 50) {
            throw new CampoInvalidoException(
                    "O nome do usuário deve possuir no máximo 50 caracteres."
            );
        }

        if (email == null || email.isBlank()) {
            throw new CampoInvalidoException(
                    "O e-mail do usuário é obrigatório."
            );
        }

        if (!emailValido(email.trim())) {
            throw new CampoInvalidoException(
                    "Informe um e-mail válido."
            );
        }

        if (email.trim().length() > 100) {
            throw new CampoInvalidoException(
                    "O e-mail deve possuir no máximo 100 caracteres."
            );
        }

        if (senha == null || senha.isBlank()) {
            throw new CampoInvalidoException(
                    "A senha do usuário é obrigatória."
            );
        }

        if (senha.length() < 6) {
            throw new CampoInvalidoException(
                    "A senha deve possuir pelo menos 6 caracteres."
            );
        }

        if (senha.length() > 100) {
            throw new CampoInvalidoException(
                    "A senha deve possuir no máximo 100 caracteres."
            );
        }
    }

    private boolean emailValido(String email) {
        return email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        );
    }
}