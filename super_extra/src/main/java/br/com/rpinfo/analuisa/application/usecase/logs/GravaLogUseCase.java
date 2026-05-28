package br.com.rpinfo.analuisa.application.usecase.logs;

import br.com.rpinfo.analuisa.application.service.LogService;
import br.com.rpinfo.analuisa.domain.model.entity.LogOperacao;
import br.com.rpinfo.analuisa.shared.Utils;
import org.springframework.stereotype.Component;

@Component
public class GravaLogUseCase {

    private final LogService logService;

    public GravaLogUseCase(LogService logService) {
        this.logService = logService;
    }

    public void execute(Integer codigoLog, String agrupamento) {
        if (codigoLog == null || !Utils.logExiste(codigoLog)) {
            throw new IllegalArgumentException("Código de log inválido.");
        }

        if (agrupamento == null || agrupamento.isBlank()) {
            throw new IllegalArgumentException("O agrupamento do log é obrigatório.");
        }

        LogOperacao log = new LogOperacao();

        log.setCodigoLog(codigoLog);
        log.setData(Utils.retornarDataAtual());
        log.setHora(Utils.retornarHoraAtualFormatada());
        log.setDescricao(Utils.retornarDescricaoLog(codigoLog));
        log.setAgrupamento(agrupamento.trim());

        logService.salvar(log);
    }
}
