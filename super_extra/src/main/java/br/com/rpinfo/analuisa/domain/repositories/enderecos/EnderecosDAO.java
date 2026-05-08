package br.com.rpinfo.analuisa.domain.repositories.enderecos;

import br.com.rpinfo.analuisa.domain.model.entity.Endereco;
import java.util.List;

public interface EnderecosDAO {

    void cadastrar(Endereco endereco);

    List<Endereco> listarTodos();

    Endereco buscarPorId(Integer id);

    boolean existePorId(Integer id);

    void atualizar(Endereco endereco);

    void deletar(Integer id);
}
