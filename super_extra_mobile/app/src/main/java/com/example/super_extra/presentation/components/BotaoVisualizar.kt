package com.example.super_extra.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.super_extra.R

private val FundoBotaoVisualizar = Color(0xFFF1F1F4)
private val CorIconeVisualizar = Color(0xFF30323A)

@Composable
fun BotaoVisualizar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.size(38.dp),
        shape = CircleShape,
        color = FundoBotaoVisualizar
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.size(38.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_eye),
                contentDescription = "Visualizar",
                tint = CorIconeVisualizar,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}