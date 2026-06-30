package com.example.super_extra.core.network

import com.example.super_extra.data.remote.api.ClientesApi
import com.example.super_extra.data.remote.api.EnderecosApi
import com.example.super_extra.data.remote.api.ProdutosApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val produtosApi: ProdutosApi by lazy {
        retrofit.create(ProdutosApi::class.java)
    }

    val clientesApi: ClientesApi by lazy {
        retrofit.create(ClientesApi::class.java)
    }

    val enderecosApi: EnderecosApi by lazy {
        retrofit.create(EnderecosApi::class.java)
    }
}