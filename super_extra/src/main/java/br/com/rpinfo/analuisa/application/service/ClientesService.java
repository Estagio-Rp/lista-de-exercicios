package br.com.rpinfo.analuisa.application.service;

import br.com.rpinfo.analuisa.application.dto.clientes.ClientesDTO;
import br.com.rpinfo.analuisa.domain.exceptions.CampoInvalidoException;
import br.com.rpinfo.analuisa.domain.exceptions.RegistroNaoEncontradoException;
import br.com.rpinfo.analuisa.domain.model.entity.Cliente;
import br.com.rpinfo.analuisa.domain.repositories.clientes.ClientesDAO;
import br.com.rpinfo.analuisa.domain.repositories.enderecos.EnderecosDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientesService {

    private final ClientesDAO clientesDAO;
    private final EnderecosDAO enderecosDAO;

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

        return clienteSalvo.getId() != null;
    }

    public List<ClientesDTO> listarClientes() {
        return clientesDAO.findAllByOrderByIdAsc()
                .stream()
                .map(ClientesDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public ClientesDTO buscarPorId(Integer id) {
        validarId(id);

        Cliente cliente = clientesDAO.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Cliente não encontrado."));

        return ClientesDTO.fromEntity(cliente);
    }

    @Transactional
    public ClientesDTO atualizarCliente(Integer id, ClientesDTO dto) {
        validarId(id);

        Cliente clienteAtual = clientesDAO.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Cliente não encontrado."));

        if (dto.getCpf() != null && clientesDAO.existsByCpfAndIdNot(dto.getCpf(), id)) {
            throw new CampoInvalidoException("CPF já cadastrado.");
        }

        clienteAtual.setNome(dto.getNome());
        clienteAtual.setEmail(dto.getEmail());
        clienteAtual.setCpf(dto.getCpf());
        clienteAtual.setTelefone(dto.getTelefone());
        clienteAtual.setEnderecoId(dto.getEnderecoId());

        validarCliente(clienteAtual);

        Cliente clienteSalvo = clientesDAO.save(clienteAtual);

        return ClientesDTO.fromEntity(clienteSalvo);
    }

    @Transactional
    public boolean deletarCliente(Integer id) {
        validarId(id);

        if (!clientesDAO.existsById(id)) {
            throw new RegistroNaoEncontradoException("Cliente não encontrado.");
        }

        clientesDAO.deleteById(id);

        return true;
    }

    private void validarCliente(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().isBlank()) {
            throw new CampoInvalidoException("O nome do cliente é obrigatório.");
        }

        if (cliente.getNome().trim().matches("\\d+")) {
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
    }

    private void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new CampoInvalidoException("ID inválido.");
        }
    }
}