package com.example.super_extra.domain.usecase

import com.example.super_extra.domain.model.Cliente
import com.example.super_extra.domain.repository.ClientesRepository

class BuscarClientesUseCase(
    private val repository: ClientesRepository
) {

    suspend fun execute(): List<Cliente> {
        return repository.buscarClientes()
    }
}