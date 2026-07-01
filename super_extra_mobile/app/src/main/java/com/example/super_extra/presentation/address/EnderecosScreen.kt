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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.super_extra.domain.model.Endereco
import com.example.super_extra.presentation.components.BotaoVisualizar
import com.example.super_extra.presentation.components.UiState

private val AzulSuperExtra = Color(0xFF1F238F)
private val FundoTela = Color(0xFFF4F4F7)
private val CinzaTexto = Color(0xFF777777)
private val CinzaBorda = Color(0xFFBDBDBD)
private val PretoIcone = Color(0xFF30323A)
private val CinzaIconeLocalizacao = Color(0xFF666666)

@Composable
fun EnderecosScreen(
    viewModel: EnderecosViewModel = viewModel(),
    onVoltarClick: () -> Unit = {},
    onVisualizarClick: (Endereco) -> Unit = {},
    onNovoEnderecoClick: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    var termoBusca by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoTela)
    ) {
        HeaderEnderecos()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp)
                .padding(top = 8.dp)
        ) {
            LinhaNavegacaoEnderecos(
                onVoltarClick = onVoltarClick
            )

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Lista de Endereços",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black
                )

                Button(
                    onClick = onNovoEnderecoClick,
                    modifier = Modifier.height(38.dp),
                    shape = RoundedCornerShape(2.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AzulSuperExtra
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp)
                ) {
                    Text(
                        text = "Novo Endereço",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            CampoPesquisaEnderecos(
                termoBusca = termoBusca,
                onTermoChange = {
                    termoBusca = it
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            when (val currentState = state) {
                is UiState.Loading -> {
                    CarregandoEnderecos()
                }

                is UiState.Success -> {
                    val enderecosFiltrados = filtrarEnderecos(
                        enderecos = currentState.data,
                        termoBusca = termoBusca
                    )

                    ListaEnderecos(
                        enderecos = enderecosFiltrados,
                        onVisualizarClick = onVisualizarClick
                    )
                }

                is UiState.Error -> {
                    ErroEnderecos(
                        mensagem = currentState.message,
                        aoTentarNovamente = {
                            viewModel.carregarEnderecos()
                        }
                    )
                }
            }
        }
    }
}

private fun filtrarEnderecos(
    enderecos: List<Endereco>,
    termoBusca: String
): List<Endereco> {
    val termoTexto = termoBusca.trim()
    val termoNumeros = termoBusca.filter { it.isDigit() }

    if (termoTexto.isBlank()) {
        return enderecos
    }

    return enderecos.filter { endereco ->
        endereco.id.toString().contains(termoTexto) ||
                endereco.rua.contains(termoTexto, ignoreCase = true) ||
                endereco.bairro.contains(termoTexto, ignoreCase = true) ||
                endereco.cep.contains(termoNumeros) ||
                endereco.numero.toString().contains(termoTexto) ||
                endereco.cidadeId.toString().contains(termoTexto)
    }
}

@Composable
private fun HeaderEnderecos() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(AzulSuperExtra)
            .padding(horizontal = 16.dp),
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
private fun LinhaNavegacaoEnderecos(
    onVoltarClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp),
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
            text = "Painel",
            fontSize = 11.sp,
            color = PretoIcone,
            modifier = Modifier.clickable {
                onVoltarClick()
            }
        )

        Text(
            text = "> Endereços",
            fontSize = 11.sp,
            color = CinzaTexto
        )
    }
}

@Composable
private fun CampoPesquisaEnderecos(
    termoBusca: String,
    onTermoChange: (String) -> Unit
) {
    OutlinedTextField(
        value = termoBusca,
        onValueChange = onTermoChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        placeholder = {
            Text(
                text = "Pesquisar por ID...",
                fontSize = 12.sp,
                color = CinzaTexto
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Pesquisar",
                tint = PretoIcone,
                modifier = Modifier.size(18.dp)
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(6.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PretoIcone,
            unfocusedBorderColor = CinzaBorda,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            cursorColor = AzulSuperExtra
        )
    )
}

@Composable
private fun CarregandoEnderecos() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = AzulSuperExtra
        )
    }
}

@Composable
private fun ListaEnderecos(
    enderecos: List<Endereco>,
    onVisualizarClick: (Endereco) -> Unit
) {
    if (enderecos.isEmpty()) {
        Text(
            text = "Nenhum endereço encontrado.",
            fontSize = 13.sp,
            color = CinzaTexto
        )
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            items(enderecos) { endereco ->
                EnderecoItem(
                    endereco = endereco,
                    onVisualizarClick = onVisualizarClick
                )
            }
        }
    }
}

@Composable
private fun EnderecoItem(
    endereco: Endereco,
    onVisualizarClick: (Endereco) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 68.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = "Endereço",
                tint = CinzaIconeLocalizacao,
                modifier = Modifier.size(34.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${endereco.rua}, ${endereco.numero}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = "ID: ${endereco.id}, ${formatarCep(endereco.cep)}",
                    fontSize = 11.sp,
                    color = CinzaTexto
                )
            }

            BotaoVisualizar(
                onClick = {
                    onVisualizarClick(endereco)
                }
            )
        }
    }
}

@Composable
private fun ErroEnderecos(
    mensagem: String,
    aoTentarNovamente: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = mensagem,
            fontSize = 13.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = aoTentarNovamente,
            colors = ButtonDefaults.buttonColors(
                containerColor = AzulSuperExtra
            )
        ) {
            Text(
                text = "Tentar novamente",
                color = Color.White
            )
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