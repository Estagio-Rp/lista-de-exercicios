package com.example.super_extra.data.remote.api

import com.example.super_extra.data.remote.dto.ProdutosDTO
import retrofit2.http.GET

interface ProdutosApi {

    @GET("api/produtos")
    suspend fun buscarProdutos(): List<ProdutosDTO>
}