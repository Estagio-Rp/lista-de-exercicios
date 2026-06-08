package br.com.rpinfo.analuisa.domain.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "logoperacoes")
public class LogOperacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "logo_id")
    private Integer id;

    @Column(name = "logo_codigo", nullable = false)
    private Integer codigoLog;

    @Column(name = "logo_data", nullable = false)
    private LocalDate data;

    @Column(name = "logo_hora", nullable = false, length = 8)
    private String hora;

    @Column(name = "logo_descricao", nullable = false, length = 255)
    private String descricao;

    @Column(name = "logo_agrupamento", nullable = false, length = 255)
    private String agrupamento;
}