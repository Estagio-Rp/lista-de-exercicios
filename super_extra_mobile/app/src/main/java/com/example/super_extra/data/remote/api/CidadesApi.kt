package com.example.super_extra.data.remote.api

import com.example.super_extra.data.remote.dto.CidadesDTO
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CidadesApi {

    @GET("api/cidades")
    suspend fun buscarCidades(): List<CidadesDTO>

    @POST("api/cidades")
    suspend fun cadastrarCidade(
        @Body cidade: CidadesDTO
    ): Response<ResponseBody>
}