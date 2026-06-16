package com.example.super_extra.data.repository

import com.example.super_extra.data.remote.api.ProdutosApi
import com.example.super_extra.domain.model.Produto
import com.example.super_extra.domain.repository.ProdutosRepository

class ProdutosRepositoryImpl(
    private val api: ProdutosApi
) : ProdutosRepository {

    override suspend fun buscarProdutos(): List<Produto> {
        return api.buscarProdutos().map { dto ->
            Produto(
                id = dto.prodId,
                nome = dto.prodNome,
                preco = dto.prodPreco,
                categoria = dto.prodCategoria,
                estoque = dto.prodEstoque
            )
        }
    }

    override suspend fun deletarProduto(id: Int) {
        api.deletarProduto(id)
    }
}