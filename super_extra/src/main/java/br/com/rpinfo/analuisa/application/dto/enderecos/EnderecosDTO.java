package br.com.rpinfo.analuisa.application.dto.enderecos;

import br.com.rpinfo.analuisa.domain.model.entity.Endereco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecosDTO {

    private Integer endeId;
    private String endeCep;
    private String endeRua;
    private Integer endeNumero;
    private String endeComplemento;
    private String endeBairro;
    private Integer endeCidaId;

    public Endereco toEntity() {
        Endereco endereco = new Endereco();

        endereco.setId(this.endeId);
        endereco.setCep(this.endeCep);
        endereco.setRua(this.endeRua);
        endereco.setNumero(this.endeNumero);
        endereco.setComplemento(this.endeComplemento);
        endereco.setBairro(this.endeBairro);
        endereco.setCidadeId(this.endeCidaId);

        return endereco;
    }

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