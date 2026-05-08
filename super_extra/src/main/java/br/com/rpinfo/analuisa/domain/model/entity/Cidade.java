package br.com.rpinfo.analuisa.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Cidade {

    private Integer id;
    private String nome;
    private String uf;

}
