package br.com.rpinfo.analuisa.application.usecase;

import br.com.rpinfo.analuisa.application.dto.clientes.ClientesDTO;
import br.com.rpinfo.analuisa.application.service.ClientesService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientesUseCase {

    private final ClientesService clientesService;

    public ClientesUseCase(ClientesService clientesService) {
        this.clientesService = clientesService;
    }

    public boolean inserirCliente(ClientesDTO dto) {
        return clientesService.inserirCliente(dto);
    }

    public List<ClientesDTO> listarClientes() {
        return clientesService.listarClientes();
    }

    public ClientesDTO buscarCliente(Integer id) {
        return clientesService.buscarPorId(id);
    }

    public ClientesDTO atualizarCliente(Integer id, ClientesDTO dto) {
        return clientesService.atualizarCliente(id, dto);
    }

    public boolean deletarCliente(Integer id) {
        return clientesService.deletarCliente(id);
    }
}