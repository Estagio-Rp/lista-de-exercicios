package br.com.rpinfo.analuisa.application.usecase;

import br.com.rpinfo.analuisa.application.dto.clientes.ClientesDTO;
import br.com.rpinfo.analuisa.application.service.ClientesService;
import br.com.rpinfo.analuisa.application.service.ServiceBase;
import br.com.rpinfo.analuisa.domain.model.entity.Cliente;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ClientesUseCase {

    public static void inserirCliente(ClientesDTO clientesDTO) {
        try (Connection connection = ServiceBase.connectionManager()) {
            ClientesService service = new ClientesService(connection);

            Cliente cliente = clientesDTO.toEntity();

            service.cadastrarCliente(cliente);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir cliente!" + e.getMessage());
        }
    }

    public static List<ClientesDTO> listarClientes() {
        try (Connection connection = ServiceBase.connectionManager()) {
            ClientesService service = new ClientesService(connection);

            List<Cliente> clientes = service.listarClientes();
            List<ClientesDTO> clientesDTO = new ArrayList<>();

            for (Cliente cliente : clientes) {
                clientesDTO.add(ClientesDTO.fromEntity(cliente));
            }
            return clientesDTO;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar clientes!" + e.getMessage());
        }
    }

    public static void atualizarCliente(Integer id, ClientesDTO clientesDTO) {
        try (Connection connection = ServiceBase.connectionManager()) {
            ClientesService service = new ClientesService(connection);

            Cliente cliente = clientesDTO.toEntity();
            cliente.setId(id);

            service.atualizarCliente(cliente);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar cliente!" + e.getMessage());
        }
    }

    public static void deletarCliente(Integer id) {
        try (Connection connection = ServiceBase.connectionManager()) {
            ClientesService service = new ClientesService(connection);

            service.deletarCliente(id);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar cliente!" + e.getMessage());
        }
    }
}
