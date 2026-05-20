package br.com.rpinfo.analuisa.domain.repositories.cidades;

import br.com.rpinfo.analuisa.domain.model.entity.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CidadesDAO extends JpaRepository<Cidade, Integer> {
    List<Cidade> findAllByOrderByIdAsc();

}