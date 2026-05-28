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
@Table(name = "logoperacao")
public class LogOperacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Integer id;

    @Column(name = "log_codigo", nullable = false)
    private Integer codigoLog;

    @Column(name = "log_data", nullable = false)
    private LocalDate data;

    @Column(name = "log_hora", nullable = false, length = 8)
    private String hora;

    @Column(name = "log_descricao", nullable = false, length = 255)
    private String descricao;

    @Column(name = "log_agrupamento", nullable = false, length = 255)
    private String agrupamento;

}
