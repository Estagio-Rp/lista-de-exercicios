package br.com.rpinfo.analuisa.domain.repositories.clientes;

import br.com.rpinfo.analuisa.domain.model.entity.Cliente;
import java.util.List;

public interface ClientesDAO {

    void cadastrar(Cliente cliente);

    List<Cliente> listarTodos();

    Cliente buscarPorId(Integer id);

    boolean existePorId(Integer id);

    void atualizar(Cliente cliente);

    void deletar(Integer id);

}

