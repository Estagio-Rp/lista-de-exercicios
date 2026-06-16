package com.example.super_extra.data.remote.dto

data class ProdutoRequestDTO(
    val prodNome: String,
    val prodPreco: Double,
    val prodCategoria: String,
    val prodEstoque: Int
)