package com.example.super_extra.data.remote.dto

data class ClienteRequestDTO(
    val nome: String,
    val email: String,
    val cpf: String,
    val telefone: String,
    val enderecoId: Int
)