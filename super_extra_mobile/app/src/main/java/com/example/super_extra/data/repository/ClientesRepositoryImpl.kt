package com.example.super_extra.data.repository

import com.example.super_extra.data.remote.api.ClientesApi
import com.example.super_extra.data.remote.dto.ClienteRequestDTO
import com.example.super_extra.domain.model.Cliente
import com.example.super_extra.domain.repository.ClientesRepository

class ClientesRepositoryImpl(
    private val api: ClientesApi
) : ClientesRepository {

    override suspend fun buscarClientes(): List<Cliente> {
        return api.buscarClientes().map { dto ->
            Cliente(
                id = dto.id,
                nome = dto.nome,
                email = dto.email,
                cpf = dto.cpf,
                telefone = dto.telefone,
                dataCadastro = dto.dataCadastro,
                enderecoId = dto.enderecoId
            )
        }
    }

    override suspend fun cadastrarCliente(cliente: Cliente) {
        val clienteRequest = ClienteRequestDTO(
            nome = cliente.nome,
            email = cliente.email,
            cpf = cliente.cpf,
            telefone = cliente.telefone,
            enderecoId = cliente.enderecoId
        )

        api.cadastrarCliente(clienteRequest)
    }

    override suspend fun atualizarCliente(cliente: Cliente) {
        val clienteRequest = ClienteRequestDTO(
            nome = cliente.nome,
            email = cliente.email,
            cpf = cliente.cpf,
            telefone = cliente.telefone,
            enderecoId = cliente.enderecoId
        )

        api.atualizarCliente(
            id = cliente.id,
            cliente = clienteRequest
        )
    }

    override suspend fun deletarCliente(id: Int) {
        api.deletarCliente(id)
    }
}
