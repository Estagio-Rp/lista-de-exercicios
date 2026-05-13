package br.com.rpinfo.analuisa.application.dto.cidades;

import br.com.rpinfo.analuisa.domain.model.entity.Cidade;
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

    public Cidade toEntity() {
        return new Cidade(
                this.id,
                this.nome,
                this.uf
        );
    }

    public static CidadesDTO fromEntity(Cidade cidade) {
        return new CidadesDTO(
                cidade.getId(),
                cidade.getNome(),
                cidade.getUf()
        );
    }
}