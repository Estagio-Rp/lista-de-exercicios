package br.com.rpinfo.analuisa.adapter.persistence;

import br.com.rpinfo.analuisa.application.port.output.UsuarioRepositoryPort;
import br.com.rpinfo.analuisa.domain.model.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuarioRepositoryAdapter
        implements UsuarioRepositoryPort {

    private final UsuarioJpaRepository usuarioJpaRepository;

    public UsuarioRepositoryAdapter(
            UsuarioJpaRepository usuarioJpaRepository
    ) {
        this.usuarioJpaRepository = usuarioJpaRepository;
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        if (email == null || email.isBlank()) {
            return Optional.empty();
        }

        String emailNormalizado = email
                .trim()
                .toLowerCase();

        return usuarioJpaRepository.findByEmailIgnoreCase(
                emailNormalizado
        );
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException(
                    "O usuário não pode ser nulo."
            );
        }

        usuario.normalizarDados();

        return usuarioJpaRepository.save(usuario);
    }

    @Override
    public boolean existePorEmail(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }

        String emailNormalizado = email
                .trim()
                .toLowerCase();

        return usuarioJpaRepository.existsByEmailIgnoreCase(
               emailNormalizado
        );
    }
}