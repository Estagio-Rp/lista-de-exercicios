package com.example.super_extra.domain.usecase

import com.example.super_extra.domain.model.Produto
import com.example.super_extra.domain.repository.ProdutosRepository

class BuscarProdutosUseCase(
    private val repository: ProdutosRepository
) {

    suspend fun execute(): List<Produto> {
        return repository.buscarProdutos()
    }
}