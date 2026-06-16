package com.example.super_extra.presentation.product

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.super_extra.R
import com.example.super_extra.domain.model.Produto
import com.example.super_extra.presentation.components.UiState
import java.util.Locale

private val AzulSuperExtra = Color(0xFF1F238F)
private val FundoTela = Color(0xFFF4F4F7)
private val CinzaTexto = Color(0xFF777777)
private val CinzaBorda = Color(0xFFBDBDBD)
private val PretoIcone = Color(0xFF30323A)

@Composable
fun ProdutosScreen(
    viewModel: ProdutosViewModel = viewModel(),
    onVoltarClick: () -> Unit = {},
    onVisualizarClick: (Produto) -> Unit = {}
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
        HeaderProdutos()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
                .padding(top = 8.dp)
        ) {
            LinhaNavegacao(
                onVoltarClick = onVoltarClick
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Lista de produtos",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black
                )

                Button(
                    onClick = {
                        // Cadastro de produto será feito depois
                    },
                    modifier = Modifier.height(34.dp),
                    shape = RoundedCornerShape(2.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AzulSuperExtra
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp)
                ) {
                    Text(
                        text = "Novo item",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            CampoPesquisa(
                termoBusca = termoBusca,
                onTermoChange = {
                    termoBusca = it
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            when (val currentState = state) {
                is UiState.Loading -> {
                    CarregandoProdutos()
                }

                is UiState.Success -> {
                    val produtosFiltrados = currentState.data.filter { produto ->
                        produto.nome.contains(termoBusca, ignoreCase = true)
                    }

                    ListaProdutos(
                        produtos = produtosFiltrados,
                        onVisualizarClick = onVisualizarClick
                    )
                }

                is UiState.Error -> {
                    ErroProdutos(
                        mensagem = currentState.message,
                        aoTentarNovamente = {
                            viewModel.carregarProdutos()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun HeaderProdutos() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .background(AzulSuperExtra)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LogoTopo()

        Text(
            text = "Olá, usuário!",
            fontSize = 13.sp,
            color = Color.White
        )
    }
}

@Composable
private fun LogoTopo() {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            BarraLogo(altura = 28)

            Spacer(modifier = Modifier.width(5.dp))

            BarraLogo(altura = 28)

            Spacer(modifier = Modifier.width(5.dp))

            BarraLogo(altura = 18)
        }

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = "SUPER EXTRA",
            fontSize = 8.sp,
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
            .width(9.dp)
            .height(altura.dp)
            .background(Color.White)
    )
}

@Composable
private fun LinhaNavegacao(
    onVoltarClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(36.dp)
                .height(32.dp)
                .clickable {
                    onVoltarClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "←",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = PretoIcone
            )
        }

        Text(
            text = "Painel",
            fontSize = 11.sp,
            color = PretoIcone,
            modifier = Modifier.clickable {
                onVoltarClick()
            }
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = "> Produtos",
            fontSize = 11.sp,
            color = CinzaTexto
        )
    }
}

@Composable
private fun CampoPesquisa(
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
                text = "Pesquisar por nome...",
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
        shape = RoundedCornerShape(6.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PretoIcone,
            unfocusedBorderColor = CinzaBorda,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}

@Composable
private fun CarregandoProdutos() {
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
private fun ListaProdutos(
    produtos: List<Produto>,
    onVisualizarClick: (Produto) -> Unit
) {
    if (produtos.isEmpty()) {
        Text(
            text = "Nenhum produto encontrado.",
            fontSize = 13.sp,
            color = CinzaTexto
        )
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            items(produtos) { produto ->
                ProdutoItem(
                    produto = produto,
                    onVisualizarClick = onVisualizarClick
                )
            }
        }
    }
}

@Composable
private fun ProdutoItem(
    produto: Produto,
    onVisualizarClick: (Produto) -> Unit
) {
    val precoFormatado = String.format(
        Locale("pt", "BR"),
        "R$ %.2f",
        produto.preco
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 70.dp),
        shape = RoundedCornerShape(10.dp),
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
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = produto.nome,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "ID: ${produto.id} | $precoFormatado | estoque: ${produto.estoque}un",
                    fontSize = 10.sp,
                    color = CinzaTexto
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Categoria: ${produto.categoria}",
                    fontSize = 10.sp,
                    color = CinzaTexto
                )
            }

            Box(
                modifier = Modifier
                    .width(36.dp)
                    .height(36.dp)
                    .clickable {
                        onVisualizarClick(produto)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_eye),
                    contentDescription = "Visualizar produto",
                    tint = PretoIcone,
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                )
            }
        }
    }
}

@Composable
private fun ErroProdutos(
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
            Text(text = "Tentar novamente")
        }
    }
}