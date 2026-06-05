package br.com.rpinfo.analuisa.domain.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "enderecos")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ende_id")
    private Integer id;

    @Column(name = "ende_cep", nullable = false, length = 8)
    private String cep;

    @Column(name = "ende_rua", nullable = false, length = 100)
    private String rua;

    @Column(name = "ende_numero", nullable = false)
    private Integer numero;

    @Column(name = "ende_complemento", length = 100)
    private String complemento;

    @Column(name = "ende_bairro", nullable = false, length = 100)
    private String bairro;

    @Column(name = "ende_cida_id", nullable = false)
    private Integer cidadeId;
}