package com.example.super_extra.presentation.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.super_extra.domain.model.Produto
import com.example.super_extra.presentation.components.UiState

@Composable
fun ProdutosScreen(
    viewModel: ProdutosViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    when (val currentState = state) {
        is UiState.Loading -> {
            CarregandoProdutos()
        }

        is UiState.Success -> {
            ListaProdutos(
                produtos = currentState.data
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

@Composable
private fun CarregandoProdutos() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Carregando produtos...")
    }
}

@Composable
private fun ListaProdutos(
    produtos: List<Produto>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Produtos",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (produtos.isEmpty()) {
            Text(text = "Nenhum produto cadastrado.")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(produtos) { produto ->
                    ProdutoItem(produto = produto)
                }
            }
        }
    }
}

@Composable
private fun ProdutoItem(
    produto: Produto
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = produto.nome,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Categoria: ${produto.categoria}")
            Text(text = "Preço: R$ ${produto.preco}")
            Text(text = "Estoque: ${produto.estoque}")
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
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = mensagem)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = aoTentarNovamente
        ) {
            Text(text = "Tentar novamente")
        }
    }
}