package br.com.rpinfo.analuisa.application.service;

import br.com.rpinfo.analuisa.domain.exceptions.CampoInvalidoException;
import br.com.rpinfo.analuisa.domain.exceptions.RegistroNaoEncontradoException;
import br.com.rpinfo.analuisa.domain.model.entity.Cidade;
import br.com.rpinfo.analuisa.domain.repositories.cidades.CidadesDAO;
import br.com.rpinfo.analuisa.domain.repositories.cidades.CidadesDAOImpl;

import java.sql.Connection;
import java.util.List;

public class CidadesService extends ServiceBase {

    private final CidadesDAO dao;

    public CidadesService(Connection connection) {
        super(connection);
        this.dao = new CidadesDAOImpl(connection);
    }

    public void cadastrarCidade(Cidade cidade) {
        validarCidade(cidade);

        this.dao.cadastrar(cidade);

        System.out.println("Cidade cadastrada com sucesso!");
    }

    public List<Cidade> listarCidades() {
        return this.dao.listarTodos();
    }

    public void atualizarCidade(Cidade cidade) {
        validarId(cidade.getId());

        if (!this.dao.existePorId(cidade.getId())) {
            throw new RegistroNaoEncontradoException("Cidade não encontrada.");
        }

        validarCidade(cidade);

        this.dao.atualizar(cidade);

        System.out.println("Cidade atualizada com sucesso!");
    }

    public void deletarCidade(Integer id) {
        validarId(id);

        if (!this.dao.existePorId(id)) {
            throw new RegistroNaoEncontradoException("Cidade não encontrada.");
        }

        this.dao.deletar(id);

        System.out.println("Cidade deletada com sucesso!");
    }

    private void validarCidade(Cidade cidade) {
        if (cidade.getNome() == null || cidade.getNome().isBlank()) {
            throw new CampoInvalidoException("O nome da cidade é obrigatório.");
        }

        if (cidade.getUf() == null || !cidade.getUf().matches("[A-Z]{2}")) {
            throw new CampoInvalidoException("A UF deve ter exatamente 2 letras.");
        }
    }

    private void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new CampoInvalidoException("ID inválido.");
        }
    }
}