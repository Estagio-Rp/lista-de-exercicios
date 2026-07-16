package com.example.super_extra.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.super_extra.core.network.RetrofitFactory
import com.example.super_extra.data.repository.AuthRepositoryImpl
import com.example.super_extra.domain.usecase.AutenticarUsuarioUseCase
import com.example.super_extra.domain.usecase.CadastrarUsuarioAuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AuthUiState(
    val carregando: Boolean = false,
    val mensagemErro: String = "",
    val mensagemSucesso: String = ""
)

class AuthViewModel : ViewModel() {

    private val repository = AuthRepositoryImpl(
        authApi = RetrofitFactory.authApi,
        tokenManager = RetrofitFactory.tokenManager()
    )

    private val autenticarUsuarioUseCase =
        AutenticarUsuarioUseCase(repository)

    private val cadastrarUsuarioUseCase =
        CadastrarUsuarioAuthUseCase(repository)

    private val _state = MutableStateFlow(
        AuthUiState()
    )

    val state: StateFlow<AuthUiState> =
        _state.asStateFlow()

    fun autenticar(
        email: String,
        senha: String,
        aoFinalizar: () -> Unit = {}
    ) {
        if (_state.value.carregando) {
            return
        }

        viewModelScope.launch {
            _state.value = AuthUiState(
                carregando = true
            )

            try {
                autenticarUsuarioUseCase.execute(
                    email = email,
                    senha = senha
                )

                _state.value = AuthUiState(
                    carregando = false,
                    mensagemSucesso = "Login realizado com sucesso."
                )

                aoFinalizar()
            } catch (e: Exception) {
                _state.value = AuthUiState(
                    carregando = false,
                    mensagemErro = e.message
                        ?: "Não foi possível realizar o login."
                )
            }
        }
    }

    fun cadastrar(
        nome: String,
        email: String,
        senha: String,
        confirmarSenha: String,
        aoFinalizar: () -> Unit = {}
    ) {
        if (_state.value.carregando) {
            return
        }

        if (senha != confirmarSenha) {
            _state.value = AuthUiState(
                mensagemErro = "As senhas não correspondem."
            )

            return
        }

        viewModelScope.launch {
            _state.value = AuthUiState(
                carregando = true
            )

            try {
                cadastrarUsuarioUseCase.execute(
                    nome = nome,
                    email = email,
                    senha = senha
                )

                _state.value = AuthUiState(
                    carregando = false,
                    mensagemSucesso = "Cadastro realizado com sucesso."
                )

                aoFinalizar()
            } catch (e: Exception) {
                _state.value = AuthUiState(
                    carregando = false,
                    mensagemErro = e.message
                        ?: "Não foi possível realizar o cadastro."
                )
            }
        }
    }

    fun possuiTokenValido(): Boolean {
        return repository.possuiTokenValido()
    }

    fun logout(
        aoFinalizar: () -> Unit = {}
    ) {
        repository.logout()

        limparMensagens()

        aoFinalizar()
    }

    fun limparMensagemErro() {
        _state.value = _state.value.copy(
            mensagemErro = ""
        )
    }

    fun limparMensagemSucesso() {
        _state.value = _state.value.copy(
            mensagemSucesso = ""
        )
    }

    fun limparMensagens() {
        _state.value = AuthUiState()
    }
}