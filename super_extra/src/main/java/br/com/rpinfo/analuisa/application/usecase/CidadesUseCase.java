package br.com.rpinfo.analuisa.application.usecase;

import br.com.rpinfo.analuisa.application.dto.cidades.CidadesDTO;
import br.com.rpinfo.analuisa.application.service.CidadesService;
import br.com.rpinfo.analuisa.domain.model.entity.Cidade;

import java.util.ArrayList;
import java.util.List;

public class CidadesUseCase {

    private final CidadesService cidadesService;

    public CidadesUseCase(CidadesService cidadesService) {
        this.cidadesService = cidadesService;
    }


    public void cadastrar(CidadesDTO dto) {
        Cidade cidade = converterParaEntidade(dto);
        cidadesService.cadastrar(cidade);
    }

    public List<CidadesDTO> listarTodos() {
        List<Cidade> cidades = cidadesService.listarTodos();
        List<CidadesDTO> cidadesDTO = new ArrayList<>();

        for (Cidade cidade : cidades) {
            cidadesDTO.add(converterParaDTO(cidade));
        }

        return cidadesDTO;
    }

    public void atualizar(Integer id, CidadesDTO dto) {
        Cidade cidade = converterParaEntidade(dto);
        cidade.setId(id);

        cidadesService.atualizar(cidade);
    }

    public void deletar(Integer id) {
        cidadesService.deletar(id);
    }

    private Cidade converterParaEntidade(CidadesDTO dto) {
        return new Cidade(
                dto.getId(),
                dto.getNome(),
                dto.getUf()
        );
    }

    private CidadesDTO converterParaDTO(Cidade cidade) {
        return new CidadesDTO(
                cidade.getId(),
                cidade.getNome(),
                cidade.getUf()
        );
    }
}

