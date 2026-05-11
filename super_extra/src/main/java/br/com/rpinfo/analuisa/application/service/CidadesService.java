package br.com.rpinfo.analuisa.application.service;

import br.com.rpinfo.analuisa.domain.exceptions.CampoInvalidoException;
import br.com.rpinfo.analuisa.domain.exceptions.RegistroNaoEncontradoException;
import br.com.rpinfo.analuisa.domain.model.entity.Cidade;
import br.com.rpinfo.analuisa.domain.repositories.cidades.CidadesDAO;

import java.util.List;

public class CidadesService {
    private final CidadesDAO cidadesDAO;

    public CidadesService(CidadesDAO cidadesDAO) {
        this.cidadesDAO = cidadesDAO;
    }

    public void cadastrar(Cidade cidade) {
        validarCidade(cidade);
        cidadesDAO.cadastrar(cidade);
    }

    public List<Cidade> listarTodos() {
        return cidadesDAO.listarTodos();
    }

    public void atualizar(Cidade cidade) {
        if (cidade.getId() == null || cidade.getId() <= 0) {
            throw new CampoInvalidoException("ID da cidade inválido.");
        }

        if (!cidadesDAO.existePorId(cidade.getId())) {
            throw new RegistroNaoEncontradoException("Cidade não encontrada.");
        }

        validarCidade(cidade);
        cidadesDAO.atualizar(cidade);
    }

    public void deletar(Integer id) {
        if (id == null || id <= 0) {
            throw new CampoInvalidoException("ID da cidade inválido.");
        }

        if (!cidadesDAO.existePorId(id)) {
            throw new RegistroNaoEncontradoException("Cidade não encontrada.");
        }

        cidadesDAO.deletar(id);
    }

    private void validarCidade(Cidade cidade) {
        if (cidade.getNome() == null || cidade.getNome().isBlank()) {
            throw new CampoInvalidoException("O nome da cidade é obrigatório.");
        }

        if (cidade.getUf() == null || !cidade.getUf().matches("[A-Z]{2}")) {
            throw new CampoInvalidoException("A UF deve ter exatamente 2 letras.");
        }
    }
}

