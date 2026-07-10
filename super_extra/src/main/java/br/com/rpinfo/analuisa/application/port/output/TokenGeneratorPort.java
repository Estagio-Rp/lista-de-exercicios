package br.com.rpinfo.analuisa.application.port.output;

import br.com.rpinfo.analuisa.domain.model.entity.Usuario;

public interface TokenGeneratorPort {

    String gerarToken(Usuario usuario);
}