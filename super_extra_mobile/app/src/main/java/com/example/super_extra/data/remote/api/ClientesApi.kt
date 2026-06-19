package com.example.super_extra.data.remote.api

import com.example.super_extra.data.remote.dto.ClienteRequestDTO
import com.example.super_extra.data.remote.dto.ClientesDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ClientesApi {

    @GET("api/clientes")
    suspend fun buscarClientes(): List<ClientesDTO>

    @POST("api/clientes")
    suspend fun cadastrarCliente(
        @Body cliente: ClienteRequestDTO
    )

    @PUT("api/clientes/{id}")
    suspend fun atualizarCliente(
        @Path("id") id: Int,
        @Body cliente: ClienteRequestDTO
    )

    @DELETE("api/clientes/{id}")
    suspend fun deletarCliente(
        @Path("id") id: Int
    )
}