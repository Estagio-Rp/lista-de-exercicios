package com.example.super_extra.domain.model

data class Produto(
    val id: Int,
    val nome: String,
    val preco: Double,
    val categoria: String,
    val estoque: Int
)