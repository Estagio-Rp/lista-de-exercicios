package com.example.super_extra.presentation.client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.super_extra.core.network.RetrofitFactory
import com.example.super_extra.data.repository.ClientesRepositoryImpl
import com.example.super_extra.domain.model.Cliente
import com.example.super_extra.domain.usecase.AtualizarClienteUseCase
import com.example.super_extra.domain.usecase.BuscarClientesUseCase
import com.example.super_extra.domain.usecase.CadastrarClienteUseCase
import com.example.super_extra.domain.usecase.DeletarClienteUseCase
import com.example.super_extra.presentation.components.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ClientesViewModel : ViewModel() {

    private val repository = ClientesRepositoryImpl(
        api = RetrofitFactory.clientesApi
    )

    private val buscarClientesUseCase = BuscarClientesUseCase(
        repository = repository
    )

    private val cadastrarClienteUseCase = CadastrarClienteUseCase(
        repository = repository
    )

    private val atualizarClienteUseCase = AtualizarClienteUseCase(
        repository = repository
    )

    private val deletarClienteUseCase = DeletarClienteUseCase(
        repository = repository
    )

    private val _state = MutableStateFlow<UiState<List<Cliente>>>(UiState.Loading)
    val state = _state.asStateFlow()

    init {
        carregarClientes()
    }

    fun carregarClientes() {
        viewModelScope.launch {
            try {
                _state.value = UiState.Loading

                val clientes = buscarClientesUseCase.execute()

                _state.value = UiState.Success(clientes)

            } catch (e: Exception) {
                _state.value = UiState.Error(
                    tratarErroCliente(
                        acao = "buscar",
                        erro = e
                    )
                )
            }
        }
    }

    fun cadastrarCliente(
        cliente: Cliente,
        aoFinalizar: () -> Unit = {},
        aoErro: (String) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                cadastrarClienteUseCase.execute(cliente)

                val clientesAtualizados = buscarClientesUseCase.execute()

                _state.value = UiState.Success(clientesAtualizados)

                aoFinalizar()

            } catch (e: Exception) {
                aoErro(
                    tratarErroCliente(
                        acao = "cadastrar",
                        erro = e
                    )
                )
            }
        }
    }

    fun atualizarCliente(
        cliente: Cliente,
        aoFinalizar: () -> Unit = {},
        aoErro: (String) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                atualizarClienteUseCase.execute(cliente)

                val clientesAtualizados = buscarClientesUseCase.execute()

                _state.value = UiState.Success(clientesAtualizados)

                aoFinalizar()

            } catch (e: Exception) {
                aoErro(
                    tratarErroCliente(
                        acao = "atualizar",
                        erro = e
                    )
                )
            }
        }
    }

    fun deletarCliente(
        id: Int,
        aoFinalizar: () -> Unit = {},
        aoErro: (String) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                deletarClienteUseCase.execute(id)

                val clientesAtualizados = buscarClientesUseCase.execute()

                _state.value = UiState.Success(clientesAtualizados)

                aoFinalizar()

            } catch (e: Exception) {
                aoErro(
                    tratarErroCliente(
                        acao = "excluir",
                        erro = e
                    )
                )
            }
        }
    }
}

private fun tratarErroCliente(
    acao: String,
    erro: Exception
): String {
    return when (erro) {
        is HttpException -> {
            when (erro.code()) {
                400 -> "Erro ao $acao cliente. Verifique os dados informados."
                404 -> "Erro ao $acao cliente. Registro ou endereço não encontrado."
                409 -> "Erro ao $acao cliente. Já existe um registro com esses dados."
                500 -> "Erro no servidor ao $acao cliente."
                else -> "Erro ao $acao cliente. Código: ${erro.code()}."
            }
        }

        is IOException -> {
            "Falha de conexão com o servidor."
        }

        else -> {
            "Erro ao $acao cliente."
        }
    }
}