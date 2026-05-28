package br.com.rpinfo.analuisa.application.dto.clientes;

import br.com.rpinfo.analuisa.domain.model.entity.Cliente;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    private Integer id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataCadastro;

    private Integer enderecoId;

    public Cliente toEntity() {
        Cliente cliente = new Cliente();

        cliente.setId(this.id);
        cliente.setNome(this.nome);
        cliente.setEmail(this.email);
        cliente.setCpf(this.cpf);
        cliente.setTelefone(this.telefone);
        cliente.setDataCadastro(this.dataCadastro);
        cliente.setEnderecoId(this.enderecoId);

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