package com.example.super_extra.domain.usecase

import com.example.super_extra.domain.model.Endereco
import com.example.super_extra.domain.repository.EnderecosRepository

class CadastrarEnderecoUseCase(
    private val repository: EnderecosRepository
) {

    suspend fun execute(endereco: Endereco) {
        repository.cadastrarEndereco(endereco)
    }
}