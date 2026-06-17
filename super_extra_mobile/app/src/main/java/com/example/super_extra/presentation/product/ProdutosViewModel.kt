package com.example.super_extra.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.super_extra.core.network.RetrofitFactory
import com.example.super_extra.data.repository.ProdutosRepositoryImpl
import com.example.super_extra.domain.model.Produto
import com.example.super_extra.domain.usecase.AtualizarProdutoUseCase
import com.example.super_extra.domain.usecase.BuscarProdutosUseCase
import com.example.super_extra.domain.usecase.CadastrarProdutoUseCase
import com.example.super_extra.domain.usecase.DeletarProdutoUseCase
import com.example.super_extra.presentation.components.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProdutosViewModel : ViewModel() {

    private val repository = ProdutosRepositoryImpl(
        api = RetrofitFactory.produtosApi
    )

    private val buscarProdutosUseCase = BuscarProdutosUseCase(
        repository = repository
    )

    private val cadastrarProdutoUseCase = CadastrarProdutoUseCase(
        repository = repository
    )

    private val atualizarProdutoUseCase = AtualizarProdutoUseCase(
        repository = repository
    )

    private val deletarProdutoUseCase = DeletarProdutoUseCase(
        repository = repository
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

    fun cadastrarProduto(
        produto: Produto,
        aoFinalizar: () -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                cadastrarProdutoUseCase.execute(produto)

                val produtosAtualizados = buscarProdutosUseCase.execute()

                _state.value = UiState.Success(produtosAtualizados)

                aoFinalizar()

            } catch (e: Exception) {
                _state.value = UiState.Error(
                    "Erro ao cadastrar produto"
                )
            }
        }
    }

    fun atualizarProduto(
        produto: Produto,
        aoFinalizar: () -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                atualizarProdutoUseCase.execute(produto)

                val produtosAtualizados = buscarProdutosUseCase.execute()

                _state.value = UiState.Success(produtosAtualizados)

                aoFinalizar()

            } catch (e: Exception) {
                _state.value = UiState.Error(
                    "Erro ao atualizar produto"
                )
            }
        }
    }

    fun deletarProduto(
        id: Int,
        aoFinalizar: () -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                deletarProdutoUseCase.execute(id)

                val produtosAtualizados = buscarProdutosUseCase.execute()

                _state.value = UiState.Success(produtosAtualizados)

                aoFinalizar()

            } catch (e: Exception) {
                _state.value = UiState.Error(
                    "Erro ao deletar produto"
                )
            }
        }
    }
}