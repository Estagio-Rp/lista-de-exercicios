package com.example.super_extra.data.remote.api

import com.example.super_extra.data.remote.dto.ViaCepEnderecoDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCepApi {

    @GET("ws/{cep}/json/")
    suspend fun buscarEnderecoPorCep(
        @Path("cep") cep: String
    ): ViaCepEnderecoDTO
}