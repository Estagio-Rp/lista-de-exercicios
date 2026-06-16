package com.example.super_extra.presentation.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.draw.drawBehind
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val AzulSuperExtra = Color(0xFF1F238F)
private val AzulClaro      = Color(0xFFEEF0FF)
private val FundoTela      = Color(0xFFF4F4F7)
private val TextoPrimario  = Color(0xFF1A1A2E)
private val TextoSecundario = Color(0xFF999999)

@Composable
fun MenuScreen(
    nomeUsuario: String = "Usuário",
    onProdutosClick: () -> Unit = {},
    onClientesClick: () -> Unit = {},
    onEnderecosClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoTela)
    ) {
        HeaderMenu(nomeUsuario = nomeUsuario)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(top = 20.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            Text(
                text = "Painel principal",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextoPrimario
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Escolha uma opção abaixo para começar.",
                fontSize = 12.sp,
                color = TextoSecundario
            )

            Spacer(modifier = Modifier.height(24.dp))

            MenuCard(
                titulo = "Produtos",
                descricao = "Gerenciar catálogo",
                icone = Icons.Outlined.Storefront,
                onClick = onProdutosClick
            )

            Spacer(modifier = Modifier.height(12.dp))

            MenuCard(
                titulo = "Clientes",
                descricao = "Base de clientes",
                icone = Icons.Outlined.People,
                onClick = onClientesClick
            )

            Spacer(modifier = Modifier.height(12.dp))

            MenuCard(
                titulo = "Endereços",
                descricao = "Locais cadastrados",
                icone = Icons.Outlined.LocationOn,
                onClick = onEnderecosClick
            )
        }
    }
}

@Composable
private fun HeaderMenu(nomeUsuario: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AzulSuperExtra)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LogoSuperExtra()

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "Bem-vindo de volta,",
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.75f)
            )
            Text(
                text = "$nomeUsuario!",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
private fun LogoSuperExtra() {
    Column(horizontalAlignment = Alignment.Start) {
        Row(verticalAlignment = Alignment.Bottom) {
            BarraLogo(altura = 22)
            Spacer(modifier = Modifier.width(4.dp))
            BarraLogo(altura = 22)
            Spacer(modifier = Modifier.width(4.dp))
            BarraLogo(altura = 15)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "SUPER EXTRA",
            fontSize = 8.sp,
            fontWeight = FontWeight.Black,
            color = Color.White,
            letterSpacing = 0.5.sp
        )
    }
}

@Composable
private fun BarraLogo(altura: Int) {
    Box(
        modifier = Modifier
            .width(7.dp)
            .height(altura.dp)
            .clip(RoundedCornerShape(1.dp))
            .background(Color.White)
    )
}

@Composable
private fun MenuCard(
    titulo: String,
    descricao: String,
    icone: ImageVector,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
        color = Color.White,
        tonalElevation = 0.dp,
        shadowElevation = 2.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawRect(
                        color = AzulSuperExtra,
                        size = androidx.compose.ui.geometry.Size(4.dp.toPx(), size.height)
                    )
                }
                .padding(start = 16.dp, end = 14.dp, top = 14.dp, bottom = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(AzulClaro),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icone,
                    contentDescription = null,
                    tint = AzulSuperExtra,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = titulo,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoPrimario
                )
                Text(
                    text = descricao,
                    fontSize = 11.sp,
                    color = TextoSecundario
                )
            }

            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(AzulSuperExtra),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.ChevronRight,
                    contentDescription = "Acessar $titulo",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}