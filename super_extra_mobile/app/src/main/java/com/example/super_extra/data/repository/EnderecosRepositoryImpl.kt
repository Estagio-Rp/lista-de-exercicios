package com.example.super_extra.data.repository

import com.example.super_extra.data.remote.api.EnderecosApi
import com.example.super_extra.data.remote.dto.EnderecoRequestDTO
import com.example.super_extra.domain.model.Endereco
import com.example.super_extra.domain.repository.EnderecosRepository

class EnderecosRepositoryImpl(
    private val api: EnderecosApi
) : EnderecosRepository {

    override suspend fun buscarEnderecos(): List<Endereco> {
        return api.buscarEnderecos().map { dto ->
            Endereco(
                id = dto.endeId,
                cep = dto.endeCep,
                rua = dto.endeRua,
                numero = dto.endeNumero,
                complemento = dto.endeComplemento,
                bairro = dto.endeBairro,
                cidadeId = dto.endeCidaId
            )
        }
    }

    override suspend fun cadastrarEndereco(endereco: Endereco) {
        api.cadastrarEndereco(endereco.toRequestDTO())
    }

    override suspend fun atualizarEndereco(endereco: Endereco) {
        api.atualizarEndereco(
            id = endereco.id,
            endereco = endereco.toRequestDTO()
        )
    }

    override suspend fun deletarEndereco(id: Int) {
        api.deletarEndereco(id)
    }
}

private fun Endereco.toRequestDTO(): EnderecoRequestDTO {
    return EnderecoRequestDTO(
        endeCep = cep,
        endeRua = rua,
        endeNumero = numero,
        endeComplemento = complemento,
        endeBairro = bairro,
        endeCidaId = cidadeId
    )
}