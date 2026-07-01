package com.example.super_extra.data.remote.api

import com.example.super_extra.data.remote.dto.CidadesDTO
import retrofit2.http.GET

interface CidadesApi {

    @GET("api/cidades")
    suspend fun buscarCidades(): List<CidadesDTO>
}