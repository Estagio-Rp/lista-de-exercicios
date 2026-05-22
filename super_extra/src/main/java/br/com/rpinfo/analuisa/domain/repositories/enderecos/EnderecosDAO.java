package br.com.rpinfo.analuisa.domain.repositories.enderecos;

import br.com.rpinfo.analuisa.domain.model.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecosDAO extends JpaRepository<Endereco, Integer> {

    List<Endereco> findAllByOrderByIdAsc();

    boolean existsByCidadeId(Integer cidadeId);
}

