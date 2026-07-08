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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
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
import com.example.super_extra.domain.model.Cliente
import com.example.super_extra.presentation.components.BotaoVisualizar
import com.example.super_extra.presentation.components.ClienteAvatar
import com.example.super_extra.presentation.components.UiState

private val AzulSuperExtra = Color(0xFF1F238F)
private val FundoTela = Color(0xFFF4F4F7)
private val CinzaTexto = Color(0xFF777777)
private val CinzaBorda = Color(0xFFBDBDBD)
private val PretoIcone = Color(0xFF30323A)

@Composable
fun ClientesScreen(
    viewModel: ClientesViewModel = viewModel(),
    onVoltarClick: () -> Unit = {},
    onVisualizarClick: (Cliente) -> Unit = {},
    onNovoClienteClick: () -> Unit = {}
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
        HeaderClientes()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
                .padding(top = 8.dp)
        ) {
            LinhaNavegacaoClientes(
                onVoltarClick = onVoltarClick
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Lista de Clientes",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black
                )

                Button(
                    onClick = onNovoClienteClick,
                    modifier = Modifier.height(36.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AzulSuperExtra
                    ),
                    contentPadding = PaddingValues(horizontal = 14.dp)
                ) {
                    Text(
                        text = "Novo cliente",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            CampoPesquisaClientes(
                termoBusca = termoBusca,
                onTermoChange = {
                    termoBusca = it
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            when (val currentState = state) {
                is UiState.Loading -> {
                    CarregandoClientes()
                }

                is UiState.Success -> {
                    val clientesFiltrados = filtrarClientes(
                        clientes = currentState.data,
                        termoBusca = termoBusca
                    )

                    ListaClientes(
                        clientes = clientesFiltrados,
                        onVisualizarClick = onVisualizarClick
                    )
                }

                is UiState.Error -> {
                    ErroClientes(
                        mensagem = currentState.message,
                        aoTentarNovamente = {
                            viewModel.carregarClientes()
                        }
                    )
                }
            }
        }
    }
}

private fun filtrarClientes(
    clientes: List<Cliente>,
    termoBusca: String
): List<Cliente> {
    val termoTexto = termoBusca.trim()
    val termoNumeros = termoBusca.filter { it.isDigit() }

    if (termoTexto.isBlank()) {
        return clientes
    }

    return clientes.filter { cliente ->
        val encontrouTexto = cliente.nome.contains(termoTexto, ignoreCase = true) ||
                cliente.email.contains(termoTexto, ignoreCase = true) ||
                cliente.id.toString().contains(termoTexto) ||
                cliente.enderecoId.toString().contains(termoTexto)

        val encontrouNumero = termoNumeros.isNotBlank() &&
                (
                        cliente.cpf.contains(termoNumeros) ||
                                cliente.telefone.contains(termoNumeros)
                        )

        encontrouTexto || encontrouNumero
    }
}

@Composable
private fun HeaderClientes() {
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
private fun LinhaNavegacaoClientes(
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
            text = "Painel",
            fontSize = 11.sp,
            color = PretoIcone,
            modifier = Modifier.clickable {
                onVoltarClick()
            }
        )

        Text(
            text = "> Clientes",
            fontSize = 11.sp,
            color = CinzaTexto
        )
    }
}

@Composable
private fun CampoPesquisaClientes(
    termoBusca: String,
    onTermoChange: (String) -> Unit
) {
    OutlinedTextField(
        value = termoBusca,
        onValueChange = onTermoChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        placeholder = {
            Text(
                text = "Pesquisar por nome, CPF, telefone ou endereço...",
                fontSize = 12.sp,
                color = CinzaTexto
            )
        },
        leadingIcon = {
            Text(
                text = "⌕",
                fontSize = 18.sp,
                color = PretoIcone
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = AzulSuperExtra,
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
private fun CarregandoClientes() {
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
private fun ListaClientes(
    clientes: List<Cliente>,
    onVisualizarClick: (Cliente) -> Unit
) {
    if (clientes.isEmpty()) {
        Text(
            text = "Nenhum cliente encontrado.",
            fontSize = 13.sp,
            color = CinzaTexto
        )
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            items(clientes) { cliente ->
                ClienteItem(
                    cliente = cliente,
                    onVisualizarClick = onVisualizarClick
                )
            }
        }
    }
}

@Composable
private fun ClienteItem(
    cliente: Cliente,
    onVisualizarClick: (Cliente) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 84.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ClienteAvatar(
                clienteId = cliente.id,
                nome = cliente.nome,
                size = 56.dp
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = cliente.nome,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "ID: ${cliente.id}",
                    fontSize = 10.sp,
                    color = CinzaTexto
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = cliente.email,
                    fontSize = 10.sp,
                    color = CinzaTexto
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "ID do endereço: ${cliente.enderecoId}",
                    fontSize = 10.sp,
                    color = CinzaTexto
                )
            }

            BotaoVisualizar(
                onClick = {
                    onVisualizarClick(cliente)
                }
            )
        }
    }
}

@Composable
private fun ErroClientes(
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