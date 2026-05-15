package br.com.rpinfo.analuisa.application.dto.clientes;


import br.com.rpinfo.analuisa.domain.model.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientesDTO {
    private Integer id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private LocalDateTime dataCadastro;
    private Integer enderecoId;

    public Cliente toEntity() {
        return new Cliente(
                this.id,
                this.nome,
                this.email,
                this.cpf,
                this.telefone,
                this.dataCadastro,
                this.enderecoId
        );
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
