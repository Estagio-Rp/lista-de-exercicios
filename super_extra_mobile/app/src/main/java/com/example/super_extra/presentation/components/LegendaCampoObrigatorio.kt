package com.example.super_extra.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val VermelhoErro = Color(0xFFD83A42)

@Composable
fun LegendaCampoObrigatorio() {
    Row {
        Text(
            text = "* ",
            fontSize = 12.sp,
            fontWeight = FontWeight.Black,
            color = VermelhoErro
        )

        Text(
            text = "Obrigatório o preenchimento do campo",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = VermelhoErro
        )
    }
}