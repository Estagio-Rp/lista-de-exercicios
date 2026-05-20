package br.com.rpinfo.analuisa.application.usecase;

import br.com.rpinfo.analuisa.application.dto.cidades.CidadesDTO;
import br.com.rpinfo.analuisa.application.service.CidadesService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CidadesUseCase {

    private final CidadesService cidadesService;

    public CidadesUseCase(CidadesService cidadesService) {
        this.cidadesService = cidadesService;
    }

    public boolean inserirCidade(CidadesDTO dto) {
        return cidadesService.inserirCidade(dto);
    }

    public List<CidadesDTO> listarCidades() {
        return cidadesService.listarCidades();
    }

    public CidadesDTO buscarCidade(Integer id) {
        return cidadesService.buscarPorId(id);
    }

    public CidadesDTO atualizarCidade(Integer id, CidadesDTO dto) {
        return cidadesService.atualizarCidade(id, dto);
    }

    public boolean deletarCidade(Integer id) {
        return cidadesService.deletarCidade(id);
    }
}