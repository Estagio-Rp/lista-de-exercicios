package com.example.super_extra.data.remote.dto

data class ProdutosDTO(
    val prodId: Int,
    val prodNome: String,
    val prodPreco: Double,
    val prodCategoria: String,
    val prodEstoque: Int
)