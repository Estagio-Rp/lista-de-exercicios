package com.example.super_extra.data.repository

import com.example.super_extra.data.local.TokenManager
import com.example.super_extra.data.remote.api.AuthApi
import com.example.super_extra.data.remote.dto.CadastroRequestDTO
import com.example.super_extra.data.remote.dto.LoginRequestDTO
import com.example.super_extra.domain.repository.AuthRepository
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun cadastrar(
        nome: String,
        email: String,
        senha: String
    ) {
        try {
            val resposta = authApi.cadastrar(
                request = CadastroRequestDTO(
                    nome = nome.trim(),
                    email = email.trim().lowercase(),
                    senha = senha
                )
            )

            if (!resposta.isSuccessful) {
                throw Exception(
                    extrairMensagemErro(
                        resposta = resposta,
                        mensagemPadrao = "Não foi possível cadastrar o usuário."
                    )
                )
            }
        } catch (e: IOException) {
            throw Exception(
                "Falha de conexão com o servidor."
            )
        }
    }

    override suspend fun autenticar(
        email: String,
        senha: String
    ) {
        try {
            val resposta = authApi.login(
                request = LoginRequestDTO(
                    email = email.trim().lowercase(),
                    senha = senha
                )
            )

            if (!resposta.isSuccessful) {
                throw Exception(
                    extrairMensagemErro(
                        resposta = resposta,
                        mensagemPadrao = "E-mail ou senha inválidos."
                    )
                )
            }

            val tokenResponse = resposta.body()
                ?: throw Exception(
                    "O servidor não retornou o token de autenticação."
                )

            if (tokenResponse.token.isBlank()) {
                throw Exception(
                    "O servidor retornou um token inválido."
                )
            }

            tokenManager.salvarToken(
                token = tokenResponse.token,
                tipo = tokenResponse.tipo.ifBlank {
                    "Bearer"
                },
                expiresIn = tokenResponse.expiresIn
            )
        } catch (e: IOException) {
            throw Exception(
                "Falha de conexão com o servidor."
            )
        }
    }

    override fun possuiTokenValido(): Boolean {
        return tokenManager.possuiTokenValido()
    }

    override fun logout() {
        tokenManager.removerToken()
    }

    private fun extrairMensagemErro(
        resposta: Response<*>,
        mensagemPadrao: String
    ): String {
        return try {
            val corpoErro = resposta.errorBody()
                ?.string()
                .orEmpty()

            if (corpoErro.isBlank()) {
                return mensagemPorStatus(
                    status = resposta.code(),
                    mensagemPadrao = mensagemPadrao
                )
            }

            val json = JSONObject(corpoErro)

            when {
                json.has("mensagem") -> {
                    json.optString(
                        "mensagem",
                        mensagemPadrao
                    )
                }

                json.has("message") -> {
                    json.optString(
                        "message",
                        mensagemPadrao
                    )
                }

                json.has("error") -> {
                    json.optString(
                        "error",
                        mensagemPadrao
                    )
                }

                else -> {
                    mensagemPorStatus(
                        status = resposta.code(),
                        mensagemPadrao = mensagemPadrao
                    )
                }
            }
        } catch (e: Exception) {
            mensagemPorStatus(
                status = resposta.code(),
                mensagemPadrao = mensagemPadrao
            )
        }
    }

    private fun mensagemPorStatus(
        status: Int,
        mensagemPadrao: String
    ): String {
        return when (status) {
            400 -> "Verifique os dados informados."
            401 -> "E-mail ou senha inválidos."
            403 -> "Você não possui permissão para realizar esta ação."
            409 -> "Já existe um usuário cadastrado com este e-mail."
            500 -> "Erro interno no servidor."
            else -> mensagemPadrao
        }
    }
}