package com.example.super_extra.domain.usecase

import com.example.super_extra.domain.repository.AuthRepository

class AutenticarUsuarioUseCase(
    private val repository: AuthRepository
) {

    suspend fun execute(
        email: String,
        senha: String
    ) {
        if (email.isBlank()) {
            throw IllegalArgumentException(
                "Informe o e-mail."
            )
        }

        if (!android.util.Patterns.EMAIL_ADDRESS
                .matcher(email.trim())
                .matches()
        ) {
            throw IllegalArgumentException(
                "Informe um e-mail válido."
            )
        }

        if (senha.isBlank()) {
            throw IllegalArgumentException(
                "Informe a senha."
            )
        }

        repository.autenticar(
            email = email.trim().lowercase(),
            senha = senha
        )
    }
}