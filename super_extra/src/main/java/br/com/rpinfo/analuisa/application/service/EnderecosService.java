package br.com.rpinfo.analuisa.application.service;

import br.com.rpinfo.analuisa.application.dto.enderecos.EnderecosDTO;
import br.com.rpinfo.analuisa.domain.exceptions.CampoInvalidoException;
import br.com.rpinfo.analuisa.domain.exceptions.RegistroNaoEncontradoException;
import br.com.rpinfo.analuisa.domain.model.entity.Endereco;
import br.com.rpinfo.analuisa.domain.repositories.cidades.CidadesDAO;
import br.com.rpinfo.analuisa.domain.repositories.clientes.ClientesDAO;
import br.com.rpinfo.analuisa.domain.repositories.enderecos.EnderecosDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnderecosService {

    private final EnderecosDAO enderecosDAO;
    private final CidadesDAO cidadesDAO;
    private final ClientesDAO clientesDAO;

    public EnderecosService(EnderecosDAO enderecosDAO, CidadesDAO cidadesDAO, ClientesDAO clientesDAO) {
        this.enderecosDAO = enderecosDAO;
        this.cidadesDAO = cidadesDAO;
        this.clientesDAO = clientesDAO;
    }

    @Transactional
    public boolean inserirEndereco(EnderecosDTO dto) {
        Endereco endereco = dto.toEntity();

        validarEndereco(endereco);

        Endereco enderecoSalvo = enderecosDAO.save(endereco);

        return enderecoSalvo.getId() != null;
    }

    public List<EnderecosDTO> listarEnderecos() {
        return enderecosDAO.findAllByOrderByIdAsc()
                .stream()
                .map(EnderecosDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public EnderecosDTO buscarPorId(Integer id) {
        validarId(id);

        Endereco endereco = enderecosDAO.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Endereço não encontrado."));

        return EnderecosDTO.fromEntity(endereco);
    }

    @Transactional
    public EnderecosDTO atualizarEndereco(Integer id, EnderecosDTO dto) {
        validarId(id);

        Endereco enderecoAtual = enderecosDAO.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Endereço não encontrado."));

        if (dto.getCep() != null) {
            String cep = dto.getCep().trim();

            if (!cep.matches("\\d{8}")) {
                throw new CampoInvalidoException("O CEP deve conter exatamente 8 números.");
            }

            enderecoAtual.setCep(cep);
        }

        if (dto.getRua() != null) {
            if (dto.getRua().isBlank()) {
                throw new CampoInvalidoException("A rua é obrigatória.");
            }

            String rua = dto.getRua().trim();

            if (rua.matches("\\d+")) {
                throw new CampoInvalidoException("A rua não pode ser apenas numérica.");
            }

            enderecoAtual.setRua(rua);
        }

        if (dto.getNumero() != null) {
            if (dto.getNumero() <= 0) {
                throw new CampoInvalidoException("O número deve ser maior que zero.");
            }

            enderecoAtual.setNumero(dto.getNumero());
        }

        if (dto.getComplemento() != null) {
            String complemento = dto.getComplemento().trim();

            if (complemento.isBlank()) {
                enderecoAtual.setComplemento(null);
            } else {
                if (complemento.matches("\\d+")) {
                    throw new CampoInvalidoException("O complemento não pode ser apenas numérico.");
                }

                enderecoAtual.setComplemento(complemento);
            }
        }

        if (dto.getBairro() != null) {
            if (dto.getBairro().isBlank()) {
                throw new CampoInvalidoException("O bairro é obrigatório.");
            }

            String bairro = dto.getBairro().trim();

            if (bairro.matches("\\d+")) {
                throw new CampoInvalidoException("O bairro não pode ser apenas numérico.");
            }

            enderecoAtual.setBairro(bairro);
        }

        if (dto.getCidadeId() != null) {
            if (dto.getCidadeId() <= 0) {
                throw new CampoInvalidoException("ID da cidade inválido.");
            }

            if (!cidadesDAO.existsById(dto.getCidadeId())) {
                throw new RegistroNaoEncontradoException("A cidade informada não existe.");
            }

            enderecoAtual.setCidadeId(dto.getCidadeId());
        }

        Endereco enderecoSalvo = enderecosDAO.save(enderecoAtual);

        return EnderecosDTO.fromEntity(enderecoSalvo);
    }


    @Transactional
    public boolean deletarEndereco(Integer id) {
        validarId(id);

        if (!enderecosDAO.existsById(id)) {
            throw new RegistroNaoEncontradoException("Endereço não encontrado.");
        }

        if (clientesDAO.existsByEnderecoId(id)) {
            throw new CampoInvalidoException("Não é possível excluir um endereço vinculado a cliente.");
        }

        enderecosDAO.deleteById(id);

        return true;
    }

    private void validarEndereco(Endereco endereco) {
        if (endereco.getCep() == null || !endereco.getCep().matches("\\d{8}")) {
            throw new CampoInvalidoException("O CEP deve conter exatamente 8 números.");
        }

        if (endereco.getRua() == null || endereco.getRua().isBlank()) {
            throw new CampoInvalidoException("A rua é obrigatória.");
        }

        if (endereco.getRua().trim().matches("\\d+")) {
            throw new CampoInvalidoException("A rua não pode ser apenas numérica.");
        }

        if (endereco.getNumero() == null || endereco.getNumero() <= 0) {
            throw new CampoInvalidoException("O número deve ser maior que zero.");
        }

        if (endereco.getComplemento() != null && !endereco.getComplemento().isBlank()
                && endereco.getComplemento().trim().matches("\\d+")) {
            throw new CampoInvalidoException("O complemento não pode ser apenas numérico.");
        }

        if (endereco.getBairro() == null || endereco.getBairro().isBlank()) {
            throw new CampoInvalidoException("O bairro é obrigatório.");
        }

        if (endereco.getBairro().trim().matches("\\d+")) {
            throw new CampoInvalidoException("O bairro não pode ser apenas numérico.");
        }

        if (endereco.getCidadeId() == null || endereco.getCidadeId() <= 0) {
            throw new CampoInvalidoException("O ID da cidade é obrigatório.");
        }

        if (!cidadesDAO.existsById(endereco.getCidadeId())) {
            throw new RegistroNaoEncontradoException("A cidade informada não existe.");
        }
    }

    private void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new CampoInvalidoException("ID inválido.");
        }
    }
}