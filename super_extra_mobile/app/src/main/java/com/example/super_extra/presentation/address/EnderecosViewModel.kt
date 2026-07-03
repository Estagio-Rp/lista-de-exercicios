package com.example.super_extra.presentation.address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.super_extra.core.network.RetrofitFactory
import com.example.super_extra.data.remote.dto.CidadesDTO
import com.example.super_extra.data.repository.EnderecosRepositoryImpl
import com.example.super_extra.domain.model.Endereco
import com.example.super_extra.domain.usecase.AtualizarEnderecoUseCase
import com.example.super_extra.domain.usecase.BuscarEnderecosUseCase
import com.example.super_extra.domain.usecase.CadastrarEnderecoUseCase
import com.example.super_extra.domain.usecase.DeletarEnderecoUseCase
import com.example.super_extra.presentation.components.UiState
import java.text.Normalizer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
    val state: StateFlow<UiState<List<Endereco>>> = _state.asStateFlow()

    init {
        carregarEnderecos()
    }

    fun carregarEnderecos() {
        viewModelScope.launch {
            _state.value = UiState.Loading

            try {
                val enderecos = buscarEnderecosUseCase.execute()
                _state.value = UiState.Success(enderecos)
            } catch (e: Exception) {
                _state.value = UiState.Error(
                    e.message ?: "Erro ao carregar endereços."
                )
            }
        }
    }

    fun cadastrarEndereco(
        endereco: Endereco,
        aoFinalizar: () -> Unit,
        aoErro: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                cadastrarEnderecoUseCase.execute(endereco)
                carregarEnderecos()
                aoFinalizar()
            } catch (e: Exception) {
                aoErro(e.message ?: "Erro ao cadastrar endereço.")
            }
        }
    }

    fun atualizarEndereco(
        endereco: Endereco,
        aoFinalizar: () -> Unit,
        aoErro: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                atualizarEnderecoUseCase.execute(endereco)
                carregarEnderecos()
                aoFinalizar()
            } catch (e: Exception) {
                aoErro(e.message ?: "Erro ao atualizar endereço.")
            }
        }
    }

    fun deletarEndereco(
        id: Int,
        aoFinalizar: () -> Unit,
        aoErro: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                deletarEnderecoUseCase.execute(id)
                carregarEnderecos()
                aoFinalizar()
            } catch (e: Exception) {
                aoErro(e.message ?: "Erro ao deletar endereço.")
            }
        }
    }

    fun buscarEnderecoPorCep(
        cep: String,
        aoEncontrar: (ResultadoBuscaCep) -> Unit,
        aoErro: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val cepNumeros = cep.filter { it.isDigit() }

                if (cepNumeros.length != 8) {
                    aoErro("CEP inválido.")
                    return@launch
                }

                val enderecoViaCep = RetrofitFactory.viaCepApi.buscarEnderecoPorCep(
                    cep = cepNumeros
                )

                if (enderecoViaCep.erro == true) {
                    aoErro("CEP não encontrado.")
                    return@launch
                }

                val cidadeNome = enderecoViaCep.localidade.orEmpty().trim()
                val cidadeUf = enderecoViaCep.uf.orEmpty().trim().uppercase()

                if (cidadeNome.isBlank() || cidadeUf.isBlank()) {
                    aoErro("Não foi possível identificar a cidade do CEP.")
                    return@launch
                }

                val cidadeId = buscarOuCadastrarCidade(
                    cidadeNome = cidadeNome,
                    cidadeUf = cidadeUf
                )

                aoEncontrar(
                    ResultadoBuscaCep(
                        rua = enderecoViaCep.logradouro.orEmpty(),
                        bairro = enderecoViaCep.bairro.orEmpty(),
                        complemento = enderecoViaCep.complemento.orEmpty(),
                        cidadeNome = cidadeNome,
                        cidadeUf = cidadeUf,
                        cidadeId = cidadeId
                    )
                )
            } catch (e: Exception) {
                aoErro(e.message ?: "Erro ao buscar CEP.")
            }
        }
    }

    private suspend fun buscarOuCadastrarCidade(
        cidadeNome: String,
        cidadeUf: String
    ): Int {
        val cidadesAntes = RetrofitFactory.cidadesApi.buscarCidades()

        val cidadeExistente = cidadesAntes.firstOrNull { cidade ->
            cidadeCorresponde(
                cidade = cidade,
                cidadeNome = cidadeNome,
                cidadeUf = cidadeUf
            )
        }

        val cidadeIdExistente = cidadeExistente?.cidaId

        if (cidadeIdExistente != null) {
            return cidadeIdExistente
        }

        val respostaCadastro = RetrofitFactory.cidadesApi.cadastrarCidade(
            cidade = CidadesDTO(
                cidaNome = cidadeNome,
                cidaUf = cidadeUf
            )
        )

        if (!respostaCadastro.isSuccessful) {
            val mensagemErro = respostaCadastro.errorBody()?.string()
            throw Exception(
                mensagemErro ?: "Não foi possível cadastrar a cidade automaticamente."
            )
        }

        val cidadesDepois = RetrofitFactory.cidadesApi.buscarCidades()

        val cidadeCadastrada = cidadesDepois.firstOrNull { cidade ->
            cidadeCorresponde(
                cidade = cidade,
                cidadeNome = cidadeNome,
                cidadeUf = cidadeUf
            )
        }

        val cidadeIdCadastrada = cidadeCadastrada?.cidaId

        return cidadeIdCadastrada
            ?: throw Exception("Cidade cadastrada, mas não foi possível recuperar o ID.")
    }

    private fun cidadeCorresponde(
        cidade: CidadesDTO,
        cidadeNome: String,
        cidadeUf: String
    ): Boolean {
        val nome = cidade.cidaNome ?: return false
        val uf = cidade.cidaUf ?: return false

        return nomesIguais(nome, cidadeNome) &&
                uf.equals(cidadeUf, ignoreCase = true)
    }

    private fun nomesIguais(
        nome1: String,
        nome2: String
    ): Boolean {
        return normalizarTexto(nome1) == normalizarTexto(nome2)
    }

    private fun normalizarTexto(
        texto: String
    ): String {
        return Normalizer.normalize(texto.trim(), Normalizer.Form.NFD)
            .replace("\\p{Mn}+".toRegex(), "")
            .lowercase()
    }
}