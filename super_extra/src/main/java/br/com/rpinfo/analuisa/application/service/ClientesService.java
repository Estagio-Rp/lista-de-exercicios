package br.com.rpinfo.analuisa.application.service;

import br.com.rpinfo.analuisa.application.dto.clientes.ClientesDTO;
import br.com.rpinfo.analuisa.domain.exceptions.CampoInvalidoException;
import br.com.rpinfo.analuisa.domain.exceptions.RegistroNaoEncontradoException;
import br.com.rpinfo.analuisa.domain.model.entity.Cliente;
import br.com.rpinfo.analuisa.domain.repositories.clientes.ClientesDAO;
import br.com.rpinfo.analuisa.domain.repositories.enderecos.EnderecosDAO;
import br.com.rpinfo.analuisa.shared.DocumentoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientesService {

    private final ClientesDAO clientesDAO;
    private final EnderecosDAO enderecosDAO;

    @Autowired
    private DocumentoUtils documentoUtils;

    public ClientesService(ClientesDAO clientesDAO, EnderecosDAO enderecosDAO) {
        this.clientesDAO = clientesDAO;
        this.enderecosDAO = enderecosDAO;
    }

    @Transactional
    public boolean inserirCliente(ClientesDTO dto) {
        Cliente cliente = dto.toEntity();

        validarCliente(cliente);

        if (clientesDAO.existsByCpf(cliente.getCpf())) {
            throw new CampoInvalidoException("CPF já cadastrado.");
        }

        Cliente clienteSalvo = clientesDAO.save(cliente);

        documentoUtils.gravaLog(13, "Cadastro de novo cliente");

        return clienteSalvo.getId() != null;
    }

    public List<ClientesDTO> listarClientes() {
        List<ClientesDTO> clientes = clientesDAO.findAllByOrderByIdAsc()
                .stream()
                .map(ClientesDTO::fromEntity)
                .collect(Collectors.toList());

        documentoUtils.gravaLog(14, "Consulta de todos os clientes");

        return clientes;
    }

    public ClientesDTO buscarPorId(Integer id) {
        validarId(id);

        Cliente cliente = clientesDAO.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Cliente não encontrado."));

        documentoUtils.gravaLog(14, "Consulta de cliente específico por ID: " + id);

        return ClientesDTO.fromEntity(cliente);
    }

    @Transactional
    public ClientesDTO atualizarCliente(Integer id, ClientesDTO dto) {
        validarId(id);

        Cliente clienteAtual = clientesDAO.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Cliente não encontrado."));

        if (dto.getClieNome() != null) {
            if (dto.getClieNome().isBlank()) {
                throw new CampoInvalidoException("O nome do cliente é obrigatório.");
            }

            String nome = dto.getClieNome().trim();

            if (nome.matches("\\d+")) {
                throw new CampoInvalidoException("O nome do cliente não pode ser apenas numérico.");
            }

            clienteAtual.setNome(nome);
        }

        if (dto.getClieEmail() != null) {
            if (dto.getClieEmail().isBlank() || !dto.getClieEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
                throw new CampoInvalidoException("O email informado é inválido.");
            }

            clienteAtual.setEmail(dto.getClieEmail().trim());
        }

        if (dto.getClieCpf() != null) {
            String cpf = dto.getClieCpf().trim();

            if (!cpf.matches("\\d{11}")) {
                throw new CampoInvalidoException("O CPF deve conter exatamente 11 números.");
            }

            if (clientesDAO.existsByCpfAndIdNot(cpf, id)) {
                throw new CampoInvalidoException("CPF já cadastrado.");
            }

            clienteAtual.setCpf(cpf);
        }

        if (dto.getClieTelefone() != null) {
            String telefone = dto.getClieTelefone().trim();

            if (!telefone.matches("\\d{10,11}")) {
                throw new CampoInvalidoException("O telefone deve conter 10 ou 11 números.");
            }

            clienteAtual.setTelefone(telefone);
        }

        if (dto.getClieEndeId() != null) {
            if (dto.getClieEndeId() <= 0) {
                throw new CampoInvalidoException("ID do endereço inválido.");
            }

            if (!enderecosDAO.existsById(dto.getClieEndeId())) {
                throw new RegistroNaoEncontradoException("O endereço informado não existe.");
            }

            clienteAtual.setEnderecoId(dto.getClieEndeId());
        }

        Cliente clienteSalvo = clientesDAO.save(clienteAtual);

        documentoUtils.gravaLog(15, "Atualização de cliente específico por ID: " + id);

        return ClientesDTO.fromEntity(clienteSalvo);
    }

    @Transactional
    public boolean deletarCliente(Integer id) {
        validarId(id);

        if (!clientesDAO.existsById(id)) {
            throw new RegistroNaoEncontradoException("Cliente não encontrado.");
        }

        clientesDAO.deleteById(id);

        documentoUtils.gravaLog(16, "Exclusão de cliente específico por ID: " + id);

        return true;
    }

    private void validarCliente(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().isBlank()) {
            throw new CampoInvalidoException("O nome do cliente é obrigatório.");
        }

        String nome = cliente.getNome().trim();

        if (nome.matches("\\d+")) {
            throw new CampoInvalidoException("O nome do cliente não pode ser apenas numérico.");
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

        if (!enderecosDAO.existsById(cliente.getEnderecoId())) {
            throw new RegistroNaoEncontradoException("O endereço informado não existe.");
        }

        cliente.setNome(nome);
        cliente.setEmail(cliente.getEmail().trim());
        cliente.setCpf(cliente.getCpf().trim());
        cliente.setTelefone(cliente.getTelefone().trim());
    }

    private void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new CampoInvalidoException("ID inválido.");
        }
    }
}