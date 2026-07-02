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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.super_extra.R
import com.example.super_extra.core.network.RetrofitFactory
import com.example.super_extra.domain.model.Endereco

private val AzulSuperExtra = Color(0xFF1F238F)
private val FundoTela = Color(0xFFF4F4F7)
private val CinzaTexto = Color(0xFF777777)
private val CinzaBotao = Color(0xFF8A8A8A)
private val CinzaBotaoClaro = Color(0xFFD4D4D4)
private val VermelhoBotao = Color(0xFFD83A42)
private val PretoIcone = Color(0xFF202124)
private val MapaFundo = Color(0xFFE8EEF2)
private val VermelhoPin = Color(0xFFE53935)

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

    var cidadeDescricao by remember(endereco.cidadeId) {
        mutableStateOf("")
    }

    LaunchedEffect(endereco.cidadeId) {
        try {
            val cidade = RetrofitFactory.cidadesApi.buscarCidades()
                .firstOrNull { cidade ->
                    cidade.cidaId == endereco.cidadeId
                }

            cidadeDescricao = if (cidade != null) {
                "${cidade.cidaNome}, ${nomeEstadoPorUf(cidade.cidaUf)}"
            } else {
                "Cidade ID: ${endereco.cidadeId}"
            }
        } catch (e: Exception) {
            cidadeDescricao = "Cidade ID: ${endereco.cidadeId}"
        }
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
                .padding(horizontal = 12.dp)
                .padding(top = 8.dp, bottom = 100.dp)
        ) {
            LinhaNavegacaoDetalhesEndereco(
                onVoltarClick = onVoltarClick
            )

            Spacer(modifier = Modifier.height(26.dp))

            Text(
                text = "${endereco.rua}, ${endereco.numero}",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                color = PretoIcone,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "${formatarCep(endereco.cep)}, ${endereco.bairro}, $cidadeDescricao",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = PretoIcone,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "ID: ${endereco.id}",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Black,
                color = PretoIcone,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(14.dp))

            MapaEnderecoPlaceholder(
                endereco = endereco,
                cidadeDescricao = cidadeDescricao
            )

            Spacer(modifier = Modifier.height(36.dp))

            BotoesAcaoEndereco(
                onEditarClick = onEditarClick,
                onDeletarClick = {
                    mostrarConfirmacaoDelete = true
                }
            )
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
            .height(66.dp)
            .background(AzulSuperExtra)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LogoSuperExtra()

        Icon(
            painter = painterResource(id = R.drawable.ic_user),
            contentDescription = "Usuário",
            tint = Color.White,
            modifier = Modifier.size(34.dp)
        )
    }
}

@Composable
private fun LogoSuperExtra() {
    Column(horizontalAlignment = Alignment.Start) {
        Row(verticalAlignment = Alignment.Bottom) {
            BarraLogo(altura = 26)
            Spacer(modifier = Modifier.width(5.dp))
            BarraLogo(altura = 26)
            Spacer(modifier = Modifier.width(5.dp))
            BarraLogo(altura = 16)
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
            .width(8.dp)
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
            .height(28.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = "Voltar",
            tint = PretoIcone,
            modifier = Modifier
                .size(22.dp)
                .clickable {
                    onVoltarClick()
                }
        )

        Text(
            text = "Detalhamento",
            fontSize = 15.sp,
            fontWeight = FontWeight.Black,
            color = Color.Black
        )
    }
}

@Composable
private fun MapaEnderecoPlaceholder(
    endereco: Endereco,
    cidadeDescricao: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        shape = RoundedCornerShape(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MapaFundo
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MapaFundo)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(14.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(7) { linha ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        repeat(3) { coluna ->
                            Text(
                                text = nomeMapaFalso(linha, coluna),
                                fontSize = 8.sp,
                                color = Color(0xFF8B9AA3),
                                lineHeight = 10.sp
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(42.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "Localização",
                    tint = VermelhoPin,
                    modifier = Modifier.size(42.dp)
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.78f))
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${endereco.rua}, ${endereco.numero}",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = PretoIcone,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = cidadeDescricao,
                    fontSize = 9.sp,
                    color = CinzaTexto,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun BotoesAcaoEndereco(
    onEditarClick: () -> Unit,
    onDeletarClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
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
            onClick = onDeletarClick,
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

private fun nomeMapaFalso(
    linha: Int,
    coluna: Int
): String {
    val nomes = listOf(
        "Rua Central",
        "Av. Principal",
        "Jardim América",
        "Centro",
        "Vila Nova",
        "Rua Paraná",
        "Praça Norte",
        "Bairro Sul",
        "Rua Pedro R."
    )

    return nomes[(linha + coluna) % nomes.size]
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

private fun nomeEstadoPorUf(uf: String): String {
    return when (uf.uppercase()) {
        "PR" -> "Paraná"
        "SC" -> "Santa Catarina"
        "RS" -> "Rio Grande do Sul"
        "SP" -> "São Paulo"
        "RJ" -> "Rio de Janeiro"
        "MG" -> "Minas Gerais"
        "MS" -> "Mato Grosso do Sul"
        "MT" -> "Mato Grosso"
        "GO" -> "Goiás"
        "BA" -> "Bahia"
        "ES" -> "Espírito Santo"
        "DF" -> "Distrito Federal"
        else -> uf.uppercase()
    }
}