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

    private Integer cidaId;
    private String cidaNome;
    private String cidaUf;

    public Cidade toEntity() {
        Cidade cidade = new Cidade();

        cidade.setId(this.cidaId);
        cidade.setNome(this.cidaNome);
        cidade.setUf(this.cidaUf);

        return cidade;
    }

    public static CidadesDTO fromEntity(Cidade cidade) {
        return new CidadesDTO(
                cidade.getId(),
                cidade.getNome(),
                cidade.getUf()
        );
    }
}