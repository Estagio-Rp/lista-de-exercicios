package com.example.super_extra.domain.repository

import com.example.super_extra.domain.model.Cliente

interface ClientesRepository {

    suspend fun buscarClientes(): List<Cliente>

    suspend fun cadastrarCliente(cliente: Cliente)

    suspend fun atualizarCliente(cliente: Cliente)

    suspend fun deletarCliente(id: Int)
}