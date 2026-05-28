package br.com.rpinfo.analuisa.domain.repositories.logs;

import br.com.rpinfo.analuisa.domain.model.entity.LogOperacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogOperacaoRepository extends JpaRepository<LogOperacao, Integer> {
}
