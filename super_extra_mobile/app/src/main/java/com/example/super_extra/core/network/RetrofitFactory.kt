package com.example.super_extra.core.network

import com.example.super_extra.data.remote.api.CidadesApi
import com.example.super_extra.data.remote.api.ClientesApi
import com.example.super_extra.data.remote.api.EnderecosApi
import com.example.super_extra.data.remote.api.ProdutosApi
import com.example.super_extra.data.remote.api.ViaCepApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    private const val BACKEND_BASE_URL = "http://10.0.2.2:8080/"
    private const val VIACEP_BASE_URL = "https://viacep.com.br/"

    private val backendRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BACKEND_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val viaCepRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(VIACEP_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val produtosApi: ProdutosApi by lazy {
        backendRetrofit.create(ProdutosApi::class.java)
    }

    val clientesApi: ClientesApi by lazy {
        backendRetrofit.create(ClientesApi::class.java)
    }

    val enderecosApi: EnderecosApi by lazy {
        backendRetrofit.create(EnderecosApi::class.java)
    }

    val cidadesApi: CidadesApi by lazy {
        backendRetrofit.create(CidadesApi::class.java)
    }

    val viaCepApi: ViaCepApi by lazy {
        viaCepRetrofit.create(ViaCepApi::class.java)
    }
}