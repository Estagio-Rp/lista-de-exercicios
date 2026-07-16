package com.example.super_extra.data.local

import android.content.Context

class TokenManager(
    context: Context
) {

    private val preferences = context.getSharedPreferences(
        PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )

    fun salvarToken(
        token: String,
        tipo: String,
        expiresIn: Long
    ) {
        val expiracaoEmMilissegundos =
            System.currentTimeMillis() + (expiresIn * 1000L)

        preferences.edit()
            .putString(KEY_TOKEN, token)
            .putString(KEY_TIPO_TOKEN, tipo)
            .putLong(KEY_EXPIRACAO, expiracaoEmMilissegundos)
            .apply()
    }

    fun buscarToken(): String? {
        if (tokenExpirou()) {
            removerToken()
            return null
        }

        return preferences.getString(KEY_TOKEN, null)
    }

    fun buscarTipoToken(): String {
        return preferences.getString(
            KEY_TIPO_TOKEN,
            DEFAULT_TOKEN_TYPE
        ) ?: DEFAULT_TOKEN_TYPE
    }

    fun possuiTokenValido(): Boolean {
        return !buscarToken().isNullOrBlank()
    }

    fun removerToken() {
        preferences.edit()
            .remove(KEY_TOKEN)
            .remove(KEY_TIPO_TOKEN)
            .remove(KEY_EXPIRACAO)
            .apply()
    }

    private fun tokenExpirou(): Boolean {
        val expiracao = preferences.getLong(
            KEY_EXPIRACAO,
            0L
        )

        if (expiracao == 0L) {
            return true
        }

        return System.currentTimeMillis() >= expiracao
    }

    companion object {
        private const val PREFERENCES_NAME = "super_extra_auth"

        private const val KEY_TOKEN = "jwt_token"
        private const val KEY_TIPO_TOKEN = "tipo_token"
        private const val KEY_EXPIRACAO = "token_expiracao"

        private const val DEFAULT_TOKEN_TYPE = "Bearer"
    }
}