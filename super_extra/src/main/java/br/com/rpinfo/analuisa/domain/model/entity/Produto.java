package br.com.rpinfo.analuisa.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class Produto {

    private Integer id;
    private String nome;
    private BigDecimal preco;
    private String categoria;
    private Integer estoque;
}
