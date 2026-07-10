package br.com.rpinfo.analuisa.application.port.output;

import br.com.rpinfo.analuisa.domain.model.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepositoryPort {

    Optional<Usuario> buscarPorEmail(String email);

    Usuario salvar(Usuario usuario);

    boolean existePorEmail(String email);
}