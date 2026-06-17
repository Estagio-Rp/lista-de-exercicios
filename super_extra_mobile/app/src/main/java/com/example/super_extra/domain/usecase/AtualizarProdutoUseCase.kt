package com.example.super_extra.domain.usecase

import com.example.super_extra.domain.model.Produto
import com.example.super_extra.domain.repository.ProdutosRepository

class AtualizarProdutoUseCase(
    private val repository: ProdutosRepository
) {

    suspend fun execute(produto: Produto) {
        repository.atualizarProduto(produto)
    }
}
