package com.example.super_extra.data.remote.dto

data class ClientesDTO(
    val id: Int,
    val nome: String,
    val email: String,
    val cpf: String,
    val telefone: String,
    val dataCadastro: String?,
    val enderecoId: Int
)