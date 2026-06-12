package com.example.super_extra.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

private val AzulSuperExtra = Color(0xFF1F238F)
private val FundoTela = Color(0xFFF4F4F7)
private val CinzaBarra = Color(0xFFB8B8B8)

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1800)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoTela),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LogoSplash()

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Carregando sistema...",
                fontSize = 11.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(8.dp))

            BarraCarregamento()
        }
    }
}

@Composable
private fun LogoSplash() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            BarraLogo(
                largura = 26,
                altura = 68
            )

            Spacer(modifier = Modifier.width(10.dp))

            BarraLogo(
                largura = 26,
                altura = 92
            )

            Spacer(modifier = Modifier.width(10.dp))

            BarraLogo(
                largura = 26,
                altura = 50
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "SUPER EXTRA",
            fontSize = 22.sp,
            fontWeight = FontWeight.Black,
            color = AzulSuperExtra
        )
    }
}

@Composable
private fun BarraLogo(
    largura: Int,
    altura: Int
) {
    Box(
        modifier = Modifier
            .width(largura.dp)
            .height(altura.dp)
            .background(
                color = AzulSuperExtra,
                shape = RoundedCornerShape(3.dp)
            )
    )
}

@Composable
private fun BarraCarregamento() {
    Box(
        modifier = Modifier
            .width(116.dp)
            .height(7.dp)
            .background(
                color = CinzaBarra,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .width(72.dp)
                .height(7.dp)
                .background(
                    color = AzulSuperExtra,
                    shape = RoundedCornerShape(20.dp)
                )
        )
    }
}