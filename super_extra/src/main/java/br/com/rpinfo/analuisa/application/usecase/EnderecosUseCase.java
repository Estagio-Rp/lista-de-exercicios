package br.com.rpinfo.analuisa.application.usecase;

import br.com.rpinfo.analuisa.application.dto.enderecos.EnderecosDTO;
import br.com.rpinfo.analuisa.application.service.EnderecosService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnderecosUseCase {

    private final EnderecosService enderecosService;

    public EnderecosUseCase(EnderecosService enderecosService) {
        this.enderecosService = enderecosService;
    }

    public boolean inserirEndereco(EnderecosDTO dto) {
        return enderecosService.inserirEndereco(dto);
    }

    public List<EnderecosDTO> listarEnderecos() {
        return enderecosService.listarEnderecos();
    }

    public EnderecosDTO buscarEndereco(Integer id) {
        return enderecosService.buscarPorId(id);
    }

    public EnderecosDTO atualizarEndereco(Integer id, EnderecosDTO dto) {
        return enderecosService.atualizarEndereco(id, dto);
    }

    public boolean deletarEndereco(Integer id) {
        return enderecosService.deletarEndereco(id);
    }
}