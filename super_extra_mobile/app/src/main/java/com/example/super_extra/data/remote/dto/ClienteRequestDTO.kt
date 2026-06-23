package com.example.super_extra.data.remote.dto

data class ClienteRequestDTO(
    val clieNome: String,
    val clieEmail: String,
    val clieCpf: String,
    val clieTelefone: String,
    val clieEndeId: Int
)