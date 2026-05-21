package br.com.rpinfo.analuisa.domain.repositories.clientes;

import br.com.rpinfo.analuisa.domain.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientesDAO extends JpaRepository<Cliente, Integer> {

    List<Cliente> findAllByOrderByIdAsc();

    boolean existsByCpf(String cpf);

    boolean existsByCpfAndIdNot(String cpf, Integer id);

    boolean existsByEnderecoId(Integer enderecoId);
}