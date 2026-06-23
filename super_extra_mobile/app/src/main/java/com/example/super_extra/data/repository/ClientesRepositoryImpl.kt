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
                id = dto.clieId,
                nome = dto.clieNome,
                email = dto.clieEmail,
                cpf = dto.clieCpf,
                telefone = dto.clieTelefone,
                dataCadastro = dto.clieDataCadastro,
                enderecoId = dto.clieEndeId
            )
        }
    }

    override suspend fun cadastrarCliente(cliente: Cliente) {
        val clienteRequest = ClienteRequestDTO(
            clieNome = cliente.nome,
            clieEmail = cliente.email,
            clieCpf = cliente.cpf,
            clieTelefone = cliente.telefone,
            clieEndeId = cliente.enderecoId
        )

        api.cadastrarCliente(clienteRequest)
    }

    override suspend fun atualizarCliente(cliente: Cliente) {
        val clienteRequest = ClienteRequestDTO(
            clieNome = cliente.nome,
            clieEmail = cliente.email,
            clieCpf = cliente.cpf,
            clieTelefone = cliente.telefone,
            clieEndeId = cliente.enderecoId
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