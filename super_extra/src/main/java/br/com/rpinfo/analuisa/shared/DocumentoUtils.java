package br.com.rpinfo.analuisa.shared;

import br.com.rpinfo.analuisa.application.usecase.logs.GravaLogUseCase;
import br.com.rpinfo.analuisa.domain.model.entity.LogOperacao;
import org.springframework.stereotype.Component;

@Component
public class DocumentoUtils {

    private final GravaLogUseCase gravaLogUseCase;

    public DocumentoUtils(GravaLogUseCase gravaLogUseCase) {
        this.gravaLogUseCase = gravaLogUseCase;
    }

    public void gravaLog(Integer codigoLog, String agrupamento) {
        gravaLogUseCase.execute(codigoLog, agrupamento);
    }
}

