package br.com.rpinfo.analuisa.application.usecase;

import br.com.rpinfo.analuisa.application.dto.cidades.CidadesDTO;
import br.com.rpinfo.analuisa.application.service.CidadesService;
import br.com.rpinfo.analuisa.application.service.ServiceBase;
import br.com.rpinfo.analuisa.domain.model.entity.Cidade;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CidadesUseCase {

    public static void inserirCidade(CidadesDTO cidadeDTO) {
        try (Connection connection = ServiceBase.connectionManager()) {
            CidadesService service = new CidadesService(connection);

            Cidade cidade = converterParaEntidade(cidadeDTO);

            service.cadastrarCidade(cidade);


        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir cidade: " + e.getMessage());
        }
    }

    public static List<CidadesDTO> listarCidades() {
        try (Connection connection = ServiceBase.connectionManager()) {
            CidadesService service = new CidadesService(connection);

            List<Cidade> cidades = service.listarCidades();
            List<CidadesDTO> cidadesDTO = new ArrayList<>();

            for (Cidade cidade : cidades) {
                cidadesDTO.add(converterParaDTO(cidade));
            }

            return cidadesDTO;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar cidades: " + e.getMessage());
        }
    }

    public static void atualizarCidade(Integer id, CidadesDTO cidadeDTO) {
        try (Connection connection = ServiceBase.connectionManager()) {
            CidadesService service = new CidadesService(connection);

            Cidade cidade = converterParaEntidade(cidadeDTO);
            cidade.setId(id);

            service.atualizarCidade(cidade);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar cidade: " + e.getMessage());
        }
    }

    public static void deletarCidade(Integer id) {
        try (Connection connection = ServiceBase.connectionManager()) {
            CidadesService service = new CidadesService(connection);

            service.deletarCidade(id);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar cidade: " + e.getMessage());
        }
    }

    private static Cidade converterParaEntidade(CidadesDTO dto) {
        return new Cidade(
                dto.getId(),
                dto.getNome(),
                dto.getUf()
        );
    }

    private static CidadesDTO converterParaDTO(Cidade cidade) {
        return new CidadesDTO(
                cidade.getId(),
                cidade.getNome(),
                cidade.getUf()
        );
    }
}