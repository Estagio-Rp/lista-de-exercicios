package br.com.rpinfo.analuisa.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class Cliente {

    private Integer id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private LocalDateTime dataCadastro;
    private Integer enderecoId;
}
