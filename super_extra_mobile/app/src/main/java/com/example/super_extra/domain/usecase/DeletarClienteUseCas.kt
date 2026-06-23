package com.example.super_extra.domain.usecase

import com.example.super_extra.domain.repository.ClientesRepository

class DeletarClienteUseCase(
    private val repository: ClientesRepository
) {

    suspend fun execute(id: Int) {
        repository.deletarCliente(id)
    }
}