package com.example.super_extra.domain.model

data class Endereco(
    val id: Int,
    val cep: String,
    val rua: String,
    val numero: Int,
    val complemento: String?,
    val bairro: String,
    val cidadeId: Int
)