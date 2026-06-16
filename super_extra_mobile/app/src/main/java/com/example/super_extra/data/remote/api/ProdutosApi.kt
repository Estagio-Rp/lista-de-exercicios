package com.example.super_extra.data.remote.api

import com.example.super_extra.data.remote.dto.ProdutoRequestDTO
import com.example.super_extra.data.remote.dto.ProdutosDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProdutosApi {

    @GET("api/produtos")
    suspend fun buscarProdutos(): List<ProdutosDTO>

    @POST("api/produtos")
    suspend fun cadastrarProduto(
        @Body produto: ProdutoRequestDTO
    )
    @DELETE("api/produtos/{id}")
    suspend fun deletarProduto(
        @Path("id") id: Int
    )
}