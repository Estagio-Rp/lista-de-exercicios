package com.example.super_extra.domain.repository

interface AuthRepository {

    suspend fun cadastrar(
        nome: String,
        email: String,
        senha: String
    )

    suspend fun autenticar(
        email: String,
        senha: String
    )

    fun possuiTokenValido(): Boolean

    fun logout()
}