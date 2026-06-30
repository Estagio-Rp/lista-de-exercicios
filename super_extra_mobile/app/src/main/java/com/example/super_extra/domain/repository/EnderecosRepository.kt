package com.example.super_extra.domain.repository

import com.example.super_extra.domain.model.Endereco

interface EnderecosRepository {

    suspend fun buscarEnderecos(): List<Endereco>

    suspend fun cadastrarEndereco(endereco: Endereco)

    suspend fun atualizarEndereco(endereco: Endereco)

    suspend fun deletarEndereco(id: Int)
}