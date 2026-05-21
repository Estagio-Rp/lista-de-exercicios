package br.com.rpinfo.analuisa.domain.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 11, unique = true)
    private String cpf;

    @Column(nullable = false, length = 11)
    private String telefone;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @Column(name = "ende_id", nullable = false)
    private Integer enderecoId;

    @PrePersist
    public void prePersist() {
        if(dataCadastro == null) {
            this.dataCadastro = LocalDateTime.now();
        }
    }

}
