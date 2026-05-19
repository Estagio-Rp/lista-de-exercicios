package br.com.rpinfo.analuisa.domain.repositories.produtos;

import br.com.rpinfo.analuisa.domain.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutosDAO extends JpaRepository<Produto, Integer> {

    List<Produto> findAllByOrderByIdAsc();
}