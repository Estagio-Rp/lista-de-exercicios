package br.com.rpinfo.analuisa.application.service;

import br.com.rpinfo.analuisa.domain.exceptions.CampoInvalidoException;
import br.com.rpinfo.analuisa.domain.exceptions.RegistroNaoEncontradoException;
import br.com.rpinfo.analuisa.domain.model.entity.Endereco;
import br.com.rpinfo.analuisa.domain.repositories.cidades.CidadesDAO;
import br.com.rpinfo.analuisa.domain.repositories.cidades.CidadesDAOImpl;
import br.com.rpinfo.analuisa.domain.repositories.enderecos.EnderecosDAO;
import br.com.rpinfo.analuisa.domain.repositories.enderecos.EnderecosDAOImpl;

import java.sql.Connection;
import java.util.List;

public class EnderecosService extends ServiceBase {

    private final EnderecosDAO enderecosDAO;
    private final CidadesDAO cidadesDAO;

    public EnderecosService(Connection connection) {
        super(connection);
        this.enderecosDAO = new EnderecosDAOImpl(connection);
        this.cidadesDAO = new CidadesDAOImpl(connection);
    }

    public void cadastrarEndereco(Endereco endereco) {
        validarEndereco(endereco);

        this.enderecosDAO.cadastrar(endereco);

        System.out.println("Endereço cadastrado com sucesso!");
    }

    public List<Endereco> listarEnderecos() {
        return this.enderecosDAO.listarTodos();
    }

    public void atualizarEndereco(Endereco endereco) {
        validarId(endereco.getId());

        if (!this.enderecosDAO.existePorId(endereco.getId())) {
            throw new RegistroNaoEncontradoException("Endereço não encontrado.");
        }

        validarEndereco(endereco);

        this.enderecosDAO.atualizar(endereco);

        System.out.println("Endereço atualizado com sucesso!");
    }

    public void deletarEndereco(Integer id) {
        validarId(id);

        if (!this.enderecosDAO.existePorId(id)) {
            throw new RegistroNaoEncontradoException("Endereço não encontrado.");
        }

        this.enderecosDAO.deletar(id);

        System.out.println("Endereço deletado com sucesso!");
    }

    private void validarEndereco(Endereco endereco) {
        if (endereco.getCep() == null || !endereco.getCep().matches("\\d{8}")) {
            throw new CampoInvalidoException("O CEP deve conter exatamente 8 números.");
        }

        if (endereco.getRua() == null || endereco.getRua().isBlank()) {
            throw new CampoInvalidoException("A rua é obrigatória.");
        }

        String rua = endereco.getRua().trim();

        if (rua.matches("\\d+")) {
            throw new CampoInvalidoException("A rua não pode ser apenas numérica.");
        }

        if (endereco.getNumero() == null || endereco.getNumero() <= 0) {
            throw new CampoInvalidoException("O número deve ser maior que zero.");
        }

        if (endereco.getComplemento() != null && !endereco.getComplemento().isBlank()) {
            String complemento = endereco.getComplemento().trim();

            if (complemento.matches("\\d+")) {
                throw new CampoInvalidoException("O complemento não pode ser apenas numérico.");
            }
        }

        if (endereco.getBairro() == null || endereco.getBairro().isBlank()) {
            throw new CampoInvalidoException("O bairro é obrigatório.");
        }

        String bairro = endereco.getBairro().trim();

        if (bairro.matches("\\d+")) {
            throw new CampoInvalidoException("O bairro não pode ser apenas numérico.");
        }

        if (endereco.getCidadeId() == null || endereco.getCidadeId() <= 0) {
            throw new CampoInvalidoException("O ID da cidade é obrigatório.");
        }

        if (!this.cidadesDAO.existePorId(endereco.getCidadeId())) {
            throw new RegistroNaoEncontradoException("A cidade informada não existe.");
        }
    }

    private void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new CampoInvalidoException("ID inválido!");
        }
    }
}