package br.com.rpinfo.analuisa.application.dto.enderecos;

import br.com.rpinfo.analuisa.domain.model.entity.Endereco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecosDTO {
    private Integer id;
    private String cep;
    private String rua;
    private Integer numero;
    private String complemento;
    private String bairro;
    private Integer cidadeId;

    public Endereco toEntity() {
        return new Endereco(this.id, this.cep, this.rua, this.numero, this.complemento, this.bairro, this.cidadeId);
    }

    //conversão de entidade para DTO
    public static EnderecosDTO fromEntity(Endereco endereco) {
        return new EnderecosDTO(
                endereco.getId(),
                endereco.getCep(),
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidadeId()
        );
    }
}
