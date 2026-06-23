package com.example.super_extra.data.remote.dto

data class ClientesDTO(
    val clieId: Int,
    val clieNome: String,
    val clieEmail: String,
    val clieCpf: String,
    val clieTelefone: String,
    val clieDataCadastro: String?,
    val clieEndeId: Int
)