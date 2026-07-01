package com.example.super_extra.presentation.address

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.super_extra.domain.model.Endereco

private val AzulSuperExtra = Color(0xFF1F238F)
private val FundoTela = Color(0xFFF4F4F7)
private val CinzaTexto = Color(0xFF777777)
private val CinzaBotao = Color(0xFF8A8A8A)
private val CinzaBotaoClaro = Color(0xFFD4D4D4)
private val VermelhoBotao = Color(0xFFD83A42)
private val PretoIcone = Color(0xFF30323A)

@Composable
fun DetalhesEnderecoScreen(
    endereco: Endereco,
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
        HeaderDetalhesEndereco()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(horizontal = 14.dp)
                .padding(top = 8.dp, bottom = 28.dp)
        ) {
            LinhaNavegacaoDetalhesEndereco(
                onVoltarClick = onVoltarClick
            )

            Spacer(modifier = Modifier.height(14.dp))

            CardDetalhamentoEndereco(
                endereco = endereco
            )

            Spacer(modifier = Modifier.height(22.dp))

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
        ConfirmarExclusaoEnderecoDialog(
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
private fun HeaderDetalhesEndereco() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .background(AzulSuperExtra)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LogoSuperExtra()

        Text(
            text = "Olá, usuário!",
            fontSize = 13.sp,
            color = Color.White
        )
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
private fun BarraLogo(
    altura: Int
) {
    Box(
        modifier = Modifier
            .width(7.dp)
            .height(altura.dp)
            .clip(RoundedCornerShape(1.dp))
            .background(Color.White)
    )
}

@Composable
private fun LinhaNavegacaoDetalhesEndereco(
    onVoltarClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = "Voltar",
            tint = PretoIcone,
            modifier = Modifier
                .size(18.dp)
                .clickable {
                    onVoltarClick()
                }
        )

        Text(
            text = "Endereços",
            fontSize = 11.sp,
            color = PretoIcone,
            modifier = Modifier.clickable {
                onVoltarClick()
            }
        )

        Text(
            text = "> Detalhamento",
            fontSize = 11.sp,
            color = CinzaTexto
        )
    }
}

@Composable
private fun CardDetalhamentoEndereco(
    endereco: Endereco
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = FundoTela
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${endereco.rua}, ${endereco.numero}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Text(
                text = "ID: ${endereco.id}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(14.dp))

            CampoDetalheEndereco(
                label = "CEP",
                valor = formatarCep(endereco.cep)
            )

            Spacer(modifier = Modifier.height(8.dp))

            CampoDetalheEndereco(
                label = "Rua",
                valor = endereco.rua
            )

            Spacer(modifier = Modifier.height(8.dp))

            CampoDetalheEndereco(
                label = "Número",
                valor = endereco.numero.toString()
            )

            Spacer(modifier = Modifier.height(8.dp))

            CampoDetalheEndereco(
                label = "Complemento",
                valor = endereco.complemento ?: "Sem complemento"
            )

            Spacer(modifier = Modifier.height(8.dp))

            CampoDetalheEndereco(
                label = "Bairro",
                valor = endereco.bairro
            )

            Spacer(modifier = Modifier.height(8.dp))

            CampoDetalheEndereco(
                label = "ID da cidade",
                valor = endereco.cidadeId.toString()
            )
        }
    }
}

@Composable
private fun CampoDetalheEndereco(
    label: String,
    valor: String
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Black,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(3.dp))

        OutlinedTextField(
            value = valor,
            onValueChange = {},
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            textStyle = TextStyle(
                fontSize = 12.sp,
                color = CinzaTexto
            ),
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Color(0xFFD0D0D0),
                disabledContainerColor = Color.White,
                disabledTextColor = CinzaTexto,
                disabledLabelColor = CinzaTexto
            )
        )
    }
}

@Composable
private fun ConfirmarExclusaoEnderecoDialog(
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
                    text = "Você irá remover esse endereço.",
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

private fun formatarCep(cep: String): String {
    val apenasNumeros = cep.filter { it.isDigit() }

    return if (apenasNumeros.length == 8) {
        "${apenasNumeros.substring(0, 5)}-${apenasNumeros.substring(5, 8)}"
    } else {
        cep
    }
}