package br.com.rpinfo.analuisa.domain.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clie_id")
    private Integer id;

    @Column(name = "clie_nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "clie_email", nullable = false, length = 100)
    private String email;

    @Column(name = "clie_cpf", nullable = false, length = 11, unique = true)
    private String cpf;

    @Column(name = "clie_telefone", nullable = false, length = 11)
    private String telefone;

    @Column(name = "clie_data_cadastro")
    private LocalDateTime dataCadastro;

    @Column(name = "clie_ende_id", nullable = false)
    private Integer enderecoId;

    @PrePersist
    public void prePersist() {
        if (this.dataCadastro == null) {
            this.dataCadastro = LocalDateTime.now();
        }
    }
}