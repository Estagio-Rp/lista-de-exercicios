package com.example.super_extra.presentation.address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.super_extra.core.network.RetrofitFactory
import com.example.super_extra.data.repository.EnderecosRepositoryImpl
import com.example.super_extra.domain.model.Endereco
import com.example.super_extra.domain.usecase.AtualizarEnderecoUseCase
import com.example.super_extra.domain.usecase.BuscarEnderecosUseCase
import com.example.super_extra.domain.usecase.CadastrarEnderecoUseCase
import com.example.super_extra.domain.usecase.DeletarEnderecoUseCase
import com.example.super_extra.presentation.components.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

data class ResultadoBuscaCep(
    val rua: String,
    val bairro: String,
    val complemento: String,
    val cidadeNome: String,
    val cidadeUf: String,
    val cidadeId: Int
)

class EnderecosViewModel : ViewModel() {

    private val repository = EnderecosRepositoryImpl(
        api = RetrofitFactory.enderecosApi
    )

    private val buscarEnderecosUseCase = BuscarEnderecosUseCase(repository)
    private val cadastrarEnderecoUseCase = CadastrarEnderecoUseCase(repository)
    private val atualizarEnderecoUseCase = AtualizarEnderecoUseCase(repository)
    private val deletarEnderecoUseCase = DeletarEnderecoUseCase(repository)

    private val _state = MutableStateFlow<UiState<List<Endereco>>>(UiState.Loading)
    val state = _state.asStateFlow()

    init {
        carregarEnderecos()
    }

    fun carregarEnderecos() {
        viewModelScope.launch {
            try {
                _state.value = UiState.Loading
                val enderecos = buscarEnderecosUseCase.execute()
                _state.value = UiState.Success(enderecos)
            } catch (e: Exception) {
                _state.value = UiState.Error(tratarErroEndereco("buscar", e))
            }
        }
    }

    fun buscarEnderecoPorCep(
        cep: String,
        aoEncontrar: (ResultadoBuscaCep) -> Unit = {},
        aoErro: (String) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                val cepNumeros = cep.filter { it.isDigit() }

                if (cepNumeros.length != 8) {
                    aoErro("Informe um CEP com 8 números.")
                    return@launch
                }

                val enderecoViaCep = RetrofitFactory.viaCepApi.buscarEnderecoPorCep(cepNumeros)

                if (enderecoViaCep.erro == true) {
                    aoErro("CEP não encontrado.")
                    return@launch
                }

                val cidadeNome = enderecoViaCep.localidade.orEmpty()
                val cidadeUf = enderecoViaCep.uf.orEmpty()

                val cidadeEncontrada = RetrofitFactory.cidadesApi.buscarCidades()
                    .firstOrNull { cidade ->
                        cidade.cidaNome.equals(cidadeNome, ignoreCase = true) &&
                                cidade.cidaUf.equals(cidadeUf, ignoreCase = true)
                    }

                if (cidadeEncontrada == null) {
                    aoErro("Cidade $cidadeNome - $cidadeUf não está cadastrada no backend.")
                    return@launch
                }

                aoEncontrar(
                    ResultadoBuscaCep(
                        rua = enderecoViaCep.logradouro.orEmpty(),
                        bairro = enderecoViaCep.bairro.orEmpty(),
                        complemento = enderecoViaCep.complemento.orEmpty(),
                        cidadeNome = cidadeNome,
                        cidadeUf = cidadeUf,
                        cidadeId = cidadeEncontrada.cidaId
                    )
                )

            } catch (e: IOException) {
                aoErro("Falha de conexão ao buscar CEP.")
            } catch (e: Exception) {
                aoErro("Erro ao buscar CEP.")
            }
        }
    }

    fun cadastrarEndereco(
        endereco: Endereco,
        aoFinalizar: () -> Unit = {},
        aoErro: (String) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                cadastrarEnderecoUseCase.execute(endereco)
                val enderecosAtualizados = buscarEnderecosUseCase.execute()
                _state.value = UiState.Success(enderecosAtualizados)
                aoFinalizar()
            } catch (e: Exception) {
                aoErro(tratarErroEndereco("cadastrar", e))
            }
        }
    }

    fun atualizarEndereco(
        endereco: Endereco,
        aoFinalizar: () -> Unit = {},
        aoErro: (String) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                atualizarEnderecoUseCase.execute(endereco)
                val enderecosAtualizados = buscarEnderecosUseCase.execute()
                _state.value = UiState.Success(enderecosAtualizados)
                aoFinalizar()
            } catch (e: Exception) {
                aoErro(tratarErroEndereco("atualizar", e))
            }
        }
    }

    fun deletarEndereco(
        id: Int,
        aoFinalizar: () -> Unit = {},
        aoErro: (String) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                deletarEnderecoUseCase.execute(id)
                val enderecosAtualizados = buscarEnderecosUseCase.execute()
                _state.value = UiState.Success(enderecosAtualizados)
                aoFinalizar()
            } catch (e: Exception) {
                aoErro(tratarErroEndereco("excluir", e))
            }
        }
    }
}

private fun tratarErroEndereco(
    acao: String,
    erro: Exception
): String {
    return when (erro) {
        is HttpException -> {
            val mensagemServidor = erro.response()
                ?.errorBody()
                ?.string()
                ?.trim()

            if (!mensagemServidor.isNullOrBlank()) {
                return mensagemServidor
            }

            when (erro.code()) {
                400 -> "Erro ao $acao endereço. Verifique os dados informados."
                404 -> "Erro ao $acao endereço. Registro ou cidade não encontrada."
                500 -> "Erro no servidor ao $acao endereço."
                else -> "Erro ao $acao endereço. Código: ${erro.code()}."
            }
        }

        is IOException -> "Falha de conexão com o servidor."

        else -> "Erro ao $acao endereço."
    }
}