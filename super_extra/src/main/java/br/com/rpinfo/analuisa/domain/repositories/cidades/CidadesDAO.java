package br.com.rpinfo.analuisa.domain.repositories.cidades;

import br.com.rpinfo.analuisa.domain.model.entity.Cidade;
import java.util.List;

public interface CidadesDAO {

    void cadastrar(Cidade cidade);

    List<Cidade> listarTodos();

    Cidade buscarPorId(Integer id);

    boolean existePorId(Integer id);

    void atualizar(Cidade cidade);

    void deletar(Integer id);
}
