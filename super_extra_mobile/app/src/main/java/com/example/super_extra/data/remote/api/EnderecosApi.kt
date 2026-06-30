package com.example.super_extra.data.remote.api

import com.example.super_extra.data.remote.dto.EnderecoRequestDTO
import com.example.super_extra.data.remote.dto.EnderecosDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EnderecosApi {

    @GET("api/enderecos")
    suspend fun buscarEnderecos(): List<EnderecosDTO>

    @POST("api/enderecos")
    suspend fun cadastrarEndereco(
        @Body endereco: EnderecoRequestDTO
    )

    @PUT("api/enderecos/{id}")
    suspend fun atualizarEndereco(
        @Path("id") id: Int,
        @Body endereco: EnderecoRequestDTO
    )

    @DELETE("api/enderecos/{id}")
    suspend fun deletarEndereco(
        @Path("id") id: Int
    )
}