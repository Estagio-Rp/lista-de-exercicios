package br.com.rpinfo.analuisa.application.service;

import br.com.rpinfo.analuisa.domain.exceptions.CampoInvalidoException;
import br.com.rpinfo.analuisa.domain.exceptions.RegistroNaoEncontradoException;
import br.com.rpinfo.analuisa.domain.model.entity.Cliente;
import br.com.rpinfo.analuisa.domain.repositories.clientes.ClientesDAO;
import br.com.rpinfo.analuisa.domain.repositories.clientes.ClientesDAOImpl;
import br.com.rpinfo.analuisa.domain.repositories.enderecos.EnderecosDAO;
import br.com.rpinfo.analuisa.domain.repositories.enderecos.EnderecosDAOImpl;

import java.sql.Connection;
import java.util.List;

public class ClientesService extends ServiceBase {

    private final ClientesDAO clientesDAO;
    private final EnderecosDAO enderecosDAO;

    public ClientesService(Connection connection) {
        super(connection);
        this.clientesDAO = new ClientesDAOImpl(connection);
        this.enderecosDAO = new EnderecosDAOImpl(connection);
    }

    public void cadastrarCliente(Cliente cliente) {
        validarCliente(cliente);

        this.clientesDAO.cadastrar(cliente);

        System.out.println("Cliente cadastrado com sucesso!");
    }

    public List<Cliente> listarClientes() {
        return this.clientesDAO.listarTodos();
    }

    public void atualizarCliente(Cliente cliente) {
        validarId(cliente.getId());

        if (!this.clientesDAO.existePorId(cliente.getId())) {
            throw new RegistroNaoEncontradoException("Cliente não encontrado.");
        }

        validarCliente(cliente);

        this.clientesDAO.atualizar(cliente);

        System.out.println("Cliente atualizado com sucesso!");
    }

    public void deletarCliente(Integer id) {
        validarId(id);

        if (!this.clientesDAO.existePorId(id)) {
            throw new RegistroNaoEncontradoException("Cliente não encontrado.");
        }

        this.clientesDAO.deletar(id);

        System.out.println("Cliente deletado com sucesso!");
    }

    private void validarCliente(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().isBlank()) {
            throw new CampoInvalidoException("O nome do cliente é obrigatório.");
        }

        if (cliente.getEmail() == null || !cliente.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new CampoInvalidoException("O email informado é inválido.");
        }

        if (cliente.getCpf() == null || !cliente.getCpf().matches("\\d{11}")) {
            throw new CampoInvalidoException("O CPF deve conter exatamente 11 números.");
        }

        if (cliente.getTelefone() == null || !cliente.getTelefone().matches("\\d{10,11}")) {
            throw new CampoInvalidoException("O telefone deve conter 10 ou 11 números, incluindo DDD.");
        }

        if (cliente.getEnderecoId() == null || cliente.getEnderecoId() <= 0) {
            throw new CampoInvalidoException("O ID do endereço é obrigatório.");
        }

        if (!this.enderecosDAO.existePorId(cliente.getEnderecoId())) {
            throw new RegistroNaoEncontradoException("O endereço informado não existe.");
        }
    }

    private void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new CampoInvalidoException("ID inválido.");
        }
    }
}
