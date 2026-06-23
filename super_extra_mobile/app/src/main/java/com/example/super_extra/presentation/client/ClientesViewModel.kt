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
                    "Erro ao buscar clientes"
                )
            }
        }
    }

    fun cadastrarCliente(
        cliente: Cliente,
        aoFinalizar: () -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                cadastrarClienteUseCase.execute(cliente)

                val clientesAtualizados = buscarClientesUseCase.execute()

                _state.value = UiState.Success(clientesAtualizados)

                aoFinalizar()

            } catch (e: Exception) {
                _state.value = UiState.Error(
                    "Erro ao cadastrar cliente"
                )
            }
        }
    }

    fun atualizarCliente(
        cliente: Cliente,
        aoFinalizar: () -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                atualizarClienteUseCase.execute(cliente)

                val clientesAtualizados = buscarClientesUseCase.execute()

                _state.value = UiState.Success(clientesAtualizados)

                aoFinalizar()

            } catch (e: Exception) {
                _state.value = UiState.Error(
                    "Erro ao atualizar cliente"
                )
            }
        }
    }

    fun deletarCliente(
        id: Int,
        aoFinalizar: () -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                deletarClienteUseCase.execute(id)

                val clientesAtualizados = buscarClientesUseCase.execute()

                _state.value = UiState.Success(clientesAtualizados)

                aoFinalizar()

            } catch (e: Exception) {
                _state.value = UiState.Error(
                    "Erro ao deletar cliente"
                )
            }
        }
    }
}