package com.example.super_extra.data.repository

import com.example.super_extra.data.remote.api.ProdutosApi
import com.example.super_extra.data.remote.dto.ProdutoRequestDTO
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

    override suspend fun cadastrarProduto(produto: Produto) {
        val produtoRequest = ProdutoRequestDTO(
            prodNome = produto.nome,
            prodPreco = produto.preco,
            prodCategoria = produto.categoria,
            prodEstoque = produto.estoque
        )

        api.cadastrarProduto(produtoRequest)
    }

    override suspend fun atualizarProduto(produto: Produto) {
        val produtoRequest = ProdutoRequestDTO(
            prodNome = produto.nome,
            prodPreco = produto.preco,
            prodCategoria = produto.categoria,
            prodEstoque = produto.estoque
        )

        api.atualizarProduto(
            id = produto.id,
            produto = produtoRequest
        )
    }

    override suspend fun deletarProduto(id: Int) {
        api.deletarProduto(id)
    }
}
