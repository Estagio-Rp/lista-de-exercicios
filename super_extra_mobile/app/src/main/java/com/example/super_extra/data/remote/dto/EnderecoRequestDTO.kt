package com.example.super_extra.data.remote.dto

data class EnderecoRequestDTO(
    val endeCep: String,
    val endeRua: String,
    val endeNumero: Int,
    val endeComplemento: String?,
    val endeBairro: String,
    val endeCidaId: Int
)