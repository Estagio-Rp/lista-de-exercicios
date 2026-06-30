package com.example.super_extra.presentation.client

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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.super_extra.R
import com.example.super_extra.domain.model.Cliente

private val AzulSuperExtra = Color(0xFF1F238F)
private val FundoTela = Color(0xFFF4F4F7)
private val CinzaTexto = Color(0xFF777777)
private val CinzaBotao = Color(0xFF8A8A8A)
private val CinzaBotaoClaro = Color(0xFFD4D4D4)
private val VermelhoBotao = Color(0xFFD83A42)
private val PretoIcone = Color(0xFF30323A)
private val RoxoAvatar = Color(0xFFD7D3F8)
private val AzulAvatar = Color(0xFF2A22D8)

@Composable
fun DetalhesClienteScreen(
    cliente: Cliente,
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
        HeaderDetalhesCliente()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(horizontal = 14.dp)
                .padding(top = 8.dp, bottom = 28.dp)
        ) {
            LinhaNavegacaoDetalhesCliente(
                onVoltarClick = onVoltarClick
            )

            Spacer(modifier = Modifier.height(14.dp))

            CardDetalhamentoCliente(
                cliente = cliente
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
        ConfirmarExclusaoClienteDialog(
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
private fun HeaderDetalhesCliente() {
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

        IconeUsuarioHeader()
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
private fun IconeUsuarioHeader() {
    Icon(
        painter = painterResource(id = R.drawable.ic_user),
        contentDescription = "Usuário",
        tint = Color.White,
        modifier = Modifier
            .width(38.dp)
            .height(38.dp)
    )
}

@Composable
private fun LinhaNavegacaoDetalhesCliente(
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
            text = "Detalhamento",
            fontSize = 14.sp,
            fontWeight = FontWeight.Black,
            color = Color.Black,
            modifier = Modifier.clickable {
                onVoltarClick()
            }
        )
    }
}

@Composable
private fun CardDetalhamentoCliente(
    cliente: Cliente
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
            AvatarClienteDetalhe()

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = primeiroNome(cliente.nome),
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Text(
                text = "ID: ${cliente.id}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(14.dp))

            CampoDetalheCliente(
                label = "Nome Completo",
                valor = cliente.nome
            )

            Spacer(modifier = Modifier.height(8.dp))

            CampoDetalheCliente(
                label = "E-mail",
                valor = cliente.email
            )

            Spacer(modifier = Modifier.height(8.dp))

            CampoDetalheCliente(
                label = "CPF",
                valor = formatarCpf(cliente.cpf)
            )

            Spacer(modifier = Modifier.height(8.dp))

            CampoDetalheCliente(
                label = "Telefone",
                valor = formatarTelefone(cliente.telefone)
            )

            Spacer(modifier = Modifier.height(8.dp))

            CampoDetalheCliente(
                label = "ID do endereço",
                valor = cliente.enderecoId.toString()
            )
        }
    }
}

@Composable
private fun AvatarClienteDetalhe() {
    Box(
        modifier = Modifier
            .size(76.dp)
            .background(
                color = RoxoAvatar,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .background(
                        color = Color(0xFF8276E8),
                        shape = CircleShape
                    )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .width(34.dp)
                    .height(14.dp)
                    .background(
                        color = AzulAvatar,
                        shape = RoundedCornerShape(50.dp)
                    )
            )
        }
    }
}

@Composable
private fun CampoDetalheCliente(
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
private fun ConfirmarExclusaoClienteDialog(
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
                    text = "Você irá remover esse cliente.",
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

private fun primeiroNome(nome: String): String {
    return nome.trim().split(" ").firstOrNull().orEmpty()
}

private fun formatarCpf(cpf: String): String {
    val apenasNumeros = cpf.filter {
        it.isDigit()
    }

    return if (apenasNumeros.length == 11) {
        "${apenasNumeros.substring(0, 3)}.${apenasNumeros.substring(3, 6)}.${apenasNumeros.substring(6, 9)}-${apenasNumeros.substring(9, 11)}"
    } else {
        cpf
    }
}

private fun formatarTelefone(telefone: String): String {
    val apenasNumeros = telefone.filter {
        it.isDigit()
    }

    return when (apenasNumeros.length) {
        10 -> "(${apenasNumeros.substring(0, 2)}) ${apenasNumeros.substring(2, 6)}-${apenasNumeros.substring(6, 10)}"
        11 -> "(${apenasNumeros.substring(0, 2)}) ${apenasNumeros.substring(2, 7)}-${apenasNumeros.substring(7, 11)}"
        else -> telefone
    }
}