package br.com.rpinfo.analuisa.application.dto.cidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CidadesDTO {

    private Integer id;
    private String nome;
    private String uf;
}