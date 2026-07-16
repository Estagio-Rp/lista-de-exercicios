package com.example.super_extra.domain.usecase

import com.example.super_extra.domain.repository.AuthRepository

class CadastrarUsuarioAuthUseCase(
    private val repository: AuthRepository
) {

    suspend fun execute(
        nome: String,
        email: String,
        senha: String
    ) {
        validarDados(
            nome = nome,
            email = email,
            senha = senha
        )

        repository.cadastrar(
            nome = nome.trim(),
            email = email.trim().lowercase(),
            senha = senha
        )
    }

    private fun validarDados(
        nome: String,
        email: String,
        senha: String
    ) {
        if (nome.isBlank()) {
            throw IllegalArgumentException(
                "Informe o nome."
            )
        }

        if (nome.trim().length < 3) {
            throw IllegalArgumentException(
                "O nome deve possuir pelo menos 3 caracteres."
            )
        }

        if (email.isBlank()) {
            throw IllegalArgumentException(
                "Informe o e-mail."
            )
        }

        if (!emailValido(email.trim())) {
            throw IllegalArgumentException(
                "Informe um e-mail válido."
            )
        }

        if (senha.isBlank()) {
            throw IllegalArgumentException(
                "Informe a senha."
            )
        }

        if (senha.length < 6) {
            throw IllegalArgumentException(
                "A senha deve possuir pelo menos 6 caracteres."
            )
        }
    }

    private fun emailValido(
        email: String
    ): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS
            .matcher(email)
            .matches()
    }
}