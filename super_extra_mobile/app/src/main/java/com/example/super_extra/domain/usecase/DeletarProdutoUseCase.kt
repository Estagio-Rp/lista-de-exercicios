package com.example.super_extra.domain.usecase

import com.example.super_extra.domain.repository.ProdutosRepository

class DeletarProdutoUseCase(
    private val repository: ProdutosRepository
) {

    suspend fun execute(id: Int) {
        repository.deletarProduto(id)
    }
}
