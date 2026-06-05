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
@Table(name = "cidades")
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cida_id")
    private Integer id;

    @Column(name = "cida_nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "cida_uf", nullable = false, length = 2)
    private String uf;
}