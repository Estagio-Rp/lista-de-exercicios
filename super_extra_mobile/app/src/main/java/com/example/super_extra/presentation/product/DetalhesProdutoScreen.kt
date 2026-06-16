package com.example.super_extra.presentation.product

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.super_extra.domain.model.Produto
import java.util.Locale

private val AzulSuperExtra = Color(0xFF1F238F)
private val FundoTela = Color(0xFFF4F4F7)
private val CinzaTexto = Color(0xFF777777)
private val CinzaBotao = Color(0xFF8A8A8A)
private val CinzaBotaoClaro = Color(0xFFD4D4D4)
private val VermelhoBotao = Color(0xFFD83A42)
private val PretoIcone = Color(0xFF30323A)

@Composable
fun DetalhesProdutoScreen(
    produto: Produto,
    onVoltarClick: () -> Unit = {},
    onEditarClick: () -> Unit = {},
    onDeletarClick: () -> Unit = {}
) {
    var mostrarConfirmacaoDelete by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoTela)
    ) {
        HeaderDetalhesProduto()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp)
                .padding(top = 14.dp)
        ) {
            LinhaTituloDetalhamento(
                onVoltarClick = onVoltarClick
            )

            Spacer(modifier = Modifier.height(28.dp))

            CardDetalhamentoProduto(
                produto = produto
            )

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onEditarClick,
                    modifier = Modifier
                        .width(140.dp)
                        .height(50.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(24.dp)
                        ),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CinzaBotao
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp)
                ) {
                    Text(
                        text = "Editar",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Button(
                    onClick = {
                        mostrarConfirmacaoDelete = true
                    },
                    modifier = Modifier
                        .width(140.dp)
                        .height(50.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(24.dp)
                        ),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VermelhoBotao
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp)
                ) {
                    Text(
                        text = "Deletar",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }

    if (mostrarConfirmacaoDelete) {
        ConfirmarExclusaoDialog(
            onCancelar = {
                mostrarConfirmacaoDelete = false
            },
            onConfirmar = {
                mostrarConfirmacaoDelete = false
                onDeletarClick()
            }
        )
    }
}

@Composable
private fun ConfirmarExclusaoDialog(
    onCancelar: () -> Unit,
    onConfirmar: () -> Unit
) {
    Dialog(
        onDismissRequest = onCancelar
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Confirmar Ação",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(38.dp))

                Text(
                    text = "Você irá remover esse produto.",
                    fontSize = 12.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "Deseja continuar?",
                    fontSize = 12.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(38.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = onCancelar,
                        modifier = Modifier
                            .width(120.dp)
                            .height(38.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CinzaBotaoClaro
                        ),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        Text(
                            text = "Cancelar",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = CinzaTexto
                        )
                    }

                    Button(
                        onClick = onConfirmar,
                        modifier = Modifier
                            .width(120.dp)
                            .height(38.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AzulSuperExtra
                        ),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        Text(
                            text = "Confirmar",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HeaderDetalhesProduto() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(82.dp)
            .background(AzulSuperExtra)
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LogoDetalhes()

        IconeUsuario()
    }
}

@Composable
private fun LogoDetalhes() {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            BarraLogoDetalhes(altura = 36)

            Spacer(modifier = Modifier.width(7.dp))

            BarraLogoDetalhes(altura = 48)

            Spacer(modifier = Modifier.width(7.dp))

            BarraLogoDetalhes(altura = 26)
        }

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "SUPER EXTRA",
            fontSize = 10.sp,
            fontWeight = FontWeight.Black,
            color = Color.White
        )
    }
}

@Composable
private fun BarraLogoDetalhes(
    altura: Int
) {
    Box(
        modifier = Modifier
            .width(15.dp)
            .height(altura.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(2.dp)
            )
    )
}

@Composable
private fun IconeUsuario() {
    Box(
        modifier = Modifier
            .width(40.dp)
            .height(40.dp)
            .border(
                width = 3.dp,
                color = Color.White,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "◉",
            fontSize = 23.sp,
            color = Color.White
        )
    }
}

@Composable
private fun LinhaTituloDetalhamento(
    onVoltarClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "←",
            fontSize = 34.sp,
            fontWeight = FontWeight.Black,
            color = PretoIcone,
            modifier = Modifier
                .width(34.dp)
                .clickable {
                    onVoltarClick()
                }
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "Detalhamento",
            fontSize = 22.sp,
            fontWeight = FontWeight.Black,
            color = Color.Black
        )
    }
}

@Composable
private fun CardDetalhamentoProduto(
    produto: Produto
) {
    val precoFormatado = String.format(
        Locale("pt", "BR"),
        "R$ %.2f",
        produto.preco
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = produto.nome,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = PretoIcone,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Produto cadastrado no sistema Super Extra.",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = CinzaTexto,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 6.dp,
                        shape = RoundedCornerShape(2.dp)
                    )
                    .background(Color.White)
                    .padding(vertical = 22.dp, horizontal = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = precoFormatado,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        color = PretoIcone
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "ID: ${produto.id} | Estoque: ${produto.estoque} | Categoria: ${produto.categoria}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = CinzaTexto,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}