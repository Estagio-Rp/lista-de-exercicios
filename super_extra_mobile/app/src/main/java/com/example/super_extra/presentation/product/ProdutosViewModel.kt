package com.example.super_extra.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.super_extra.core.network.RetrofitFactory
import com.example.super_extra.data.repository.ProdutosRepositoryImpl
import com.example.super_extra.domain.model.Produto
import com.example.super_extra.domain.usecase.BuscarProdutosUseCase
import com.example.super_extra.presentation.components.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProdutosViewModel : ViewModel() {

    private val buscarProdutosUseCase = BuscarProdutosUseCase(
        repository = ProdutosRepositoryImpl(
            api = RetrofitFactory.produtosApi
        )
    )

    private val _state = MutableStateFlow<UiState<List<Produto>>>(UiState.Loading)
    val state = _state.asStateFlow()

    init {
        carregarProdutos()
    }

    fun carregarProdutos() {
        viewModelScope.launch {
            try {
                _state.value = UiState.Loading

                val produtos = buscarProdutosUseCase.execute()

                _state.value = UiState.Success(produtos)

            } catch (e: Exception) {
                _state.value = UiState.Error(
                    "Erro ao buscar produtos"
                )
            }
        }
    }
}