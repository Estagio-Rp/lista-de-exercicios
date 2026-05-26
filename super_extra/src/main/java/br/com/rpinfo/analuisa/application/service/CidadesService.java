package br.com.rpinfo.analuisa.application.service;

import br.com.rpinfo.analuisa.application.dto.cidades.CidadesDTO;
import br.com.rpinfo.analuisa.domain.exceptions.CampoInvalidoException;
import br.com.rpinfo.analuisa.domain.exceptions.RegistroNaoEncontradoException;
import br.com.rpinfo.analuisa.domain.model.entity.Cidade;
import br.com.rpinfo.analuisa.domain.repositories.cidades.CidadesDAO;
import br.com.rpinfo.analuisa.domain.repositories.enderecos.EnderecosDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CidadesService {

    private final CidadesDAO cidadesDAO;
    private final EnderecosDAO enderecosDAO;

    public CidadesService(CidadesDAO cidadesDAO, EnderecosDAO enderecosDAO) {
        this.cidadesDAO = cidadesDAO;
        this.enderecosDAO = enderecosDAO;
    }

    @Transactional
    public boolean inserirCidade(CidadesDTO dto) {
        Cidade cidade = dto.toEntity();

        validarCidade(cidade);

        Cidade cidadeSalva = cidadesDAO.save(cidade);

        return cidadeSalva.getId() != null;
    }

    public List<CidadesDTO> listarCidades() {
        return cidadesDAO.findAllByOrderByIdAsc()
                .stream()
                .map(CidadesDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public CidadesDTO buscarPorId(Integer id) {
        validarId(id);

        Cidade cidade = cidadesDAO.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Cidade não encontrada."));

        return CidadesDTO.fromEntity(cidade);
    }

    @Transactional
    public CidadesDTO atualizarCidade(Integer id, CidadesDTO dto) {
        validarId(id);

        Cidade cidadeAtual = cidadesDAO.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Cidade não encontrada."));

        if (dto.getNome() != null) {
            if (dto.getNome().isBlank()) {
                throw new CampoInvalidoException("O nome da cidade é obrigatório.");
            }

            String nome = dto.getNome().trim();

            if (nome.matches("\\d+")) {
                throw new CampoInvalidoException("O nome da cidade não pode ser apenas numérico.");
            }

            cidadeAtual.setNome(nome);
        }

        if (dto.getUf() != null) {
            if (dto.getUf().isBlank()) {
                throw new CampoInvalidoException("A UF é obrigatória.");
            }

            String uf = dto.getUf().trim().toUpperCase();

            if (!uf.matches("[A-Z]{2}")) {
                throw new CampoInvalidoException("A UF deve ter exatamente 2 letras.");
            }

            cidadeAtual.setUf(uf);
        }

        Cidade cidadeSalva = cidadesDAO.save(cidadeAtual);

        return CidadesDTO.fromEntity(cidadeSalva);
    }

    @Transactional
    public boolean deletarCidade(Integer id) {
        validarId(id);

        if (!cidadesDAO.existsById(id)) {
            throw new RegistroNaoEncontradoException("Cidade não encontrada.");
        }

        if (enderecosDAO.existsByCidadeId(id)) {
            throw new CampoInvalidoException("Não é possível excluir uma cidade vinculada a endereço.");
        }

        cidadesDAO.deleteById(id);

        return true;
    }

    private void validarCidade(Cidade cidade) {
        if (cidade.getNome() == null || cidade.getNome().isBlank()) {
            throw new CampoInvalidoException("O nome da cidade é obrigatório.");
        }

        String nome = cidade.getNome().trim();

        if (nome.matches("\\d+")) {
            throw new CampoInvalidoException("O nome da cidade não pode ser apenas numérico.");
        }

        if (cidade.getUf() == null || cidade.getUf().isBlank()) {
            throw new CampoInvalidoException("A UF é obrigatória.");
        }

        String uf = cidade.getUf().trim().toUpperCase();

        if (!uf.matches("[A-Z]{2}")) {
            throw new CampoInvalidoException("A UF deve ter exatamente 2 letras.");
        }

        cidade.setNome(nome);
        cidade.setUf(uf);
    }

    private void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new CampoInvalidoException("ID inválido.");
        }
    }
}