package com.example.super_extra.presentation.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val AzulSuperExtra = Color(0xFF1F238F)
private val FundoTela = Color(0xFFF4F4F7)
private val CinzaTexto = Color(0xFF777777)
private val CinzaCard = Color(0xFFF7F7FA)
private val PretoIcone = Color(0xFF30323A)

@Composable
fun MenuScreen(
    onProdutosClick: () -> Unit = {},
    onClientesClick: () -> Unit = {},
    onEnderecosClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoTela)
    ) {
        HeaderMenu()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 14.dp)
                .padding(top = 42.dp, bottom = 40.dp)
        ) {
            Text(
                text = "Bem-vindo ao painel!",
                fontSize = 17.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Escolha uma opção abaixo para começar.",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = CinzaTexto
            )

            Spacer(modifier = Modifier.height(52.dp))

            MenuCard(
                titulo = "Produtos",
                onClick = onProdutosClick
            )

            Spacer(modifier = Modifier.height(32.dp))

            MenuCard(
                titulo = "Clientes",
                onClick = onClientesClick
            )

            Spacer(modifier = Modifier.height(32.dp))

            MenuCard(
                titulo = "Endereços",
                onClick = onEnderecosClick
            )
        }
    }
}

@Composable
private fun HeaderMenu() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(AzulSuperExtra)
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LogoSuperExtra()

        Text(
            text = "Olá, usuário!",
            fontSize = 15.sp,
            color = Color.White
        )
    }
}

@Composable
private fun LogoSuperExtra() {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            BarraLogo(altura = 30)

            Spacer(modifier = Modifier.width(6.dp))

            BarraLogo(altura = 30)

            Spacer(modifier = Modifier.width(6.dp))

            BarraLogo(altura = 20)
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "SUPER EXTRA",
            fontSize = 9.sp,
            fontWeight = FontWeight.Black,
            color = Color.White
        )
    }
}

@Composable
private fun BarraLogo(
    altura: Int
) {
    Box(
        modifier = Modifier
            .width(10.dp)
            .height(altura.dp)
            .background(Color.White)
    )
}

@Composable
private fun MenuCard(
    titulo: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(112.dp)
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(1.dp)
            )
            .clickable {
                onClick()
            },
        color = CinzaCard,
        shape = RoundedCornerShape(1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 32.dp, end = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = titulo,
                fontSize = 16.sp,
                color = CinzaTexto
            )

            Box(
                modifier = Modifier.size(38.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "➜",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = PretoIcone
                )
            }
        }
    }
}