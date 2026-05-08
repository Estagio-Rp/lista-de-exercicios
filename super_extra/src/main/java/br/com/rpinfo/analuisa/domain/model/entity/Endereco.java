package br.com.rpinfo.analuisa.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Endereco {

    private Integer id;
    private String cep;
    private String rua;
    private Integer numero;
    private String complemento;
    private String bairro;
    private Integer cidadeId;
}
