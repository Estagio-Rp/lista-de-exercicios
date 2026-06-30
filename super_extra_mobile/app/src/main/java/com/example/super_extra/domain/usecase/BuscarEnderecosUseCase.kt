package com.example.super_extra.domain.usecase

import com.example.super_extra.domain.model.Endereco
import com.example.super_extra.domain.repository.EnderecosRepository

class BuscarEnderecosUseCase(
    private val repository: EnderecosRepository
) {

    suspend fun execute(): List<Endereco> {
        return repository.buscarEnderecos()
    }
}