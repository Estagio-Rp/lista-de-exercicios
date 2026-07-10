package br.com.rpinfo.analuisa.application.port.input;

import br.com.rpinfo.analuisa.domain.model.entity.Perfil;
import br.com.rpinfo.analuisa.domain.model.entity.Usuario;

public interface CadastrarUsuarioInputPort {

    Usuario cadastrar(
            String nome,
            String email,
            String senha,
            Perfil perfil
    );
}