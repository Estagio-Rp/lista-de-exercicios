package com.example.super_extra.domain.usecase

import com.example.super_extra.domain.model.Cliente
import com.example.super_extra.domain.repository.ClientesRepository

class CadastrarClienteUseCase(
    private val repository: ClientesRepository
) {

    suspend fun execute(cliente: Cliente) {
        repository.cadastrarCliente(cliente)
    }
}