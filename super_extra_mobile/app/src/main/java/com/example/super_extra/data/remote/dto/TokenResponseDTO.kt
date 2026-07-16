package com.example.super_extra.data.remote.dto

data class TokenResponseDTO(
    val token: String,
    val tipo: String,
    val expiresIn: Long
)