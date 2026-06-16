package com.example.super_extra.domain.repository

import com.example.super_extra.domain.model.Produto

interface ProdutosRepository {

    suspend fun buscarProdutos(): List<Produto>

    suspend fun deletarProduto(id: Int)
}