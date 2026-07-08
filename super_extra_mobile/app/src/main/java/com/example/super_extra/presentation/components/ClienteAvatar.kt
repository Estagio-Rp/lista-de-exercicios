package com.example.super_extra.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.super_extra.R

@Composable
fun ClienteAvatar(
    clienteId: Int?,
    nome: String?,
    size: Dp = 56.dp
) {
    val fotoRes = remember(clienteId, nome) {
        obterFotoCliente(clienteId, nome)
    }

    if (fotoRes != null) {
        Image(
            painter = painterResource(id = fotoRes),
            contentDescription = "Foto do cliente $nome",
            modifier = Modifier
                .size(size)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = "Avatar do cliente",
                modifier = Modifier.size(size * 0.65f),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@DrawableRes
private fun obterFotoCliente(
    clienteId: Int?,
    nome: String?
): Int? {
    return when {
        clienteId == 1 -> R.drawable.foto_cliente_1
        nome?.contains("ana", ignoreCase = true) == true -> R.drawable.foto_cliente_1
        else -> null
    }
}