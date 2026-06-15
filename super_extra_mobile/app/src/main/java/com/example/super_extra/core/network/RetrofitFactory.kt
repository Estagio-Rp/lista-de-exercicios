package com.example.super_extra.core.network

import com.example.super_extra.data.remote.api.ProdutosApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    private const val BASE_URL = "http://10.0.2.2:8080/"

    val produtosApi: ProdutosApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProdutosApi::class.java)
    }
}