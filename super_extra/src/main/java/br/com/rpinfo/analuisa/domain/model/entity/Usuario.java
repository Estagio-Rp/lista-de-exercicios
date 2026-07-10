package br.com.rpinfo.analuisa.domain.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usua_codigo")
    private Integer codigo;

    @Column(name = "usua_nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "usua_email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "usua_senha", nullable = false, length = 255)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "usua_perfil", nullable = false, length = 30)
    private Perfil perfil;

    @Column(name = "usua_cadastros", nullable = false, length = 1)
    private String cadastros;

    @Column(name = "usua_entradas", nullable = false, length = 1)
    private String entradas;

    @Column(name = "usua_saidas", nullable = false, length = 1)
    private String saidas;

    @Column(name = "usua_cancel", nullable = false, length = 1)
    private String cancelamentos;

    @Column(name = "usua_relat", nullable = false, length = 1)
    private String relatorios;

    @Column(name = "usua_config", nullable = false, length = 1)
    private String configuracoes;

    public Usuario(
            String nome,
            String email,
            String senha,
            Perfil perfil
    ) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;

        aplicarPermissoesDoPerfil();
    }

    @PrePersist
    public void prePersist() {
        normalizarDados();

        if (perfil == null) {
            perfil = Perfil.CLIENTE;
        }

        preencherPermissoesAusentes();
    }

    public void normalizarDados() {
        if (nome != null) {
            nome = nome.trim();
        }

        if (email != null) {
            email = email.trim().toLowerCase();
        }
    }

    public void aplicarPermissoesDoPerfil() {
        if (perfil == Perfil.ADMINISTRADOR) {
            liberarTodasPermissoes();
            return;
        }

        bloquearTodasPermissoes();
    }

    public void liberarTodasPermissoes() {
        this.cadastros = "S";
        this.entradas = "S";
        this.saidas = "S";
        this.cancelamentos = "S";
        this.relatorios = "S";
        this.configuracoes = "S";
    }

    public void bloquearTodasPermissoes() {
        this.cadastros = "N";
        this.entradas = "N";
        this.saidas = "N";
        this.cancelamentos = "N";
        this.relatorios = "N";
        this.configuracoes = "N";
    }

    private void preencherPermissoesAusentes() {
        if (cadastros == null || cadastros.isBlank()) {
            cadastros = "N";
        }

        if (entradas == null || entradas.isBlank()) {
            entradas = "N";
        }

        if (saidas == null || saidas.isBlank()) {
            saidas = "N";
        }

        if (cancelamentos == null || cancelamentos.isBlank()) {
            cancelamentos = "N";
        }

        if (relatorios == null || relatorios.isBlank()) {
            relatorios = "N";
        }

        if (configuracoes == null || configuracoes.isBlank()) {
            configuracoes = "N";
        }
    }
}