package br.com.rpinfo.analuisa.application.usecase;

import br.com.rpinfo.analuisa.application.dto.enderecos.EnderecosDTO;
import br.com.rpinfo.analuisa.application.service.EnderecosService;
import br.com.rpinfo.analuisa.application.service.ServiceBase;
import br.com.rpinfo.analuisa.domain.model.entity.Endereco;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class EnderecosUseCase {

    public static void inserirEnderecos(EnderecosDTO enderecosDTO) {
        try (Connection connection = ServiceBase.connectionManager()) {
            EnderecosService service = new EnderecosService(connection);

            Endereco endereco = enderecosDTO.toEntity();

            service.cadastrarEndereco(endereco);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir endereco: " + e.getMessage());
        }
    }

    public static List<EnderecosDTO> listarEnderecos() {
        try (Connection connection = ServiceBase.connectionManager()) {
            EnderecosService service = new EnderecosService(connection);

            List<Endereco> enderecos = service.listarEnderecos();
            List<EnderecosDTO> enderecosDTO = new ArrayList<>();

            for (Endereco endereco : enderecos) {
                enderecosDTO.add(EnderecosDTO.fromEntity(endereco));
            }

            return enderecosDTO;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar endereços: " + e.getMessage());
        }
    }

    public static void atualizarEnderecos(Integer id, EnderecosDTO enderecosDTO) {
        try (Connection connection = ServiceBase.connectionManager()) {
            EnderecosService service = new EnderecosService(connection);

            Endereco endereco = enderecosDTO.toEntity();
            endereco.setId(id);

            service.atualizarEndereco(endereco);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar endereco: " + e.getMessage());
        }
    }

    public static void deletarEnderecos(Integer id) {
        try (Connection connection = ServiceBase.connectionManager()) {
            EnderecosService service = new EnderecosService(connection);

            service.deletarEndereco(id);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar endereco: " + e.getMessage());
        }
    }
}
