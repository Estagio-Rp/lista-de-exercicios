package com.example.super_extra.domain.usecase

import com.example.super_extra.domain.repository.EnderecosRepository

class DeletarEnderecoUseCase(
    private val repository: EnderecosRepository
) {

    suspend fun execute(id: Int) {
        repository.deletarEndereco(id)
    }
}