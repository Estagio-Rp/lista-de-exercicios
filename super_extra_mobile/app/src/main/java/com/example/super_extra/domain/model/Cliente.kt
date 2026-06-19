package com.example.super_extra.domain.model

data class Cliente(
    val id: Int,
    val nome: String,
    val email: String,
    val cpf: String,
    val telefone: String,
    val dataCadastro: String?,
    val enderecoId: Int
)
