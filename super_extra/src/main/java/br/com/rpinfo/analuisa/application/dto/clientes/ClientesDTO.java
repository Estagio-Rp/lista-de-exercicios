package br.com.rpinfo.analuisa.application.dto.clientes;

import br.com.rpinfo.analuisa.domain.model.entity.Cliente;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientesDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer clieId;

    private String clieNome;
    private String clieEmail;
    private String clieCpf;
    private String clieTelefone;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime clieDataCadastro;

    private Integer clieEndeId;

    public Cliente toEntity() {
        Cliente cliente = new Cliente();

        cliente.setNome(this.clieNome);
        cliente.setEmail(this.clieEmail);
        cliente.setCpf(this.clieCpf);
        cliente.setTelefone(this.clieTelefone);
        cliente.setEnderecoId(this.clieEndeId);

        return cliente;
    }

    public static ClientesDTO fromEntity(Cliente cliente) {
        return new ClientesDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getCpf(),
                cliente.getTelefone(),
                cliente.getDataCadastro(),
                cliente.getEnderecoId()
        );
    }
}