package com.example.super_extra.data.remote.api

import com.example.super_extra.data.remote.dto.CadastroRequestDTO
import com.example.super_extra.data.remote.dto.LoginRequestDTO
import com.example.super_extra.data.remote.dto.TokenResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/cadastro")
    suspend fun cadastrar(
        @Body request: CadastroRequestDTO
    ): Response<Unit>

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequestDTO
    ): Response<TokenResponseDTO>
}