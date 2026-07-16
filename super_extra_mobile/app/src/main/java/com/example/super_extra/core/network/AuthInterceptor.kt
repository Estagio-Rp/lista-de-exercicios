package com.example.super_extra.core.network

import com.example.super_extra.data.local.TokenManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(
        chain: Interceptor.Chain
    ): Response {
        val requisicaoOriginal = chain.request()

        val token = tokenManager.buscarToken()

        if (token.isNullOrBlank()) {
            return chain.proceed(requisicaoOriginal)
        }

        val tipoToken = tokenManager.buscarTipoToken()

        val requisicaoAutenticada = requisicaoOriginal
            .newBuilder()
            .header(
                "Authorization",
                "$tipoToken $token"
            )
            .build()

        return chain.proceed(requisicaoAutenticada)
    }
}