package com.example.super_extra

import android.app.Application
import com.example.super_extra.core.network.RetrofitFactory

class SuperExtraApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        RetrofitFactory.inicializar(
            context = applicationContext
        )
    }
}