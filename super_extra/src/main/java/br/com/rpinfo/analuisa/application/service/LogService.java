package br.com.rpinfo.analuisa.application.service;

import br.com.rpinfo.analuisa.domain.model.entity.LogOperacao;
import br.com.rpinfo.analuisa.domain.repositories.logs.LogOperacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LogService {
    private final LogOperacaoRepository logOperacaoRepository;

    public LogService(LogOperacaoRepository logOperacaoRepository) {
        this.logOperacaoRepository = logOperacaoRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void salvar(LogOperacao log){
        logOperacaoRepository.save(log);
    }
}
