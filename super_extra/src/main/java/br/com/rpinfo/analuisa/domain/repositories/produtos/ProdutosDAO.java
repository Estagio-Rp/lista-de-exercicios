package br.com.rpinfo.analuisa.domain.repositories.produtos;

import br.com.rpinfo.analuisa.domain.model.entity.Produto;
import java.util.List;

public interface ProdutosDAO {
    void cadastrar(Produto produto);

    List<Produto> listarTodos();

    Produto buscarPorId(Integer id);

    boolean existePorId(Integer id);

    void atualizar(Produto produto);

    void deletar(Integer id);

}
