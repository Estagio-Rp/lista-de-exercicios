package com.example.super_extra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.super_extra.domain.model.Produto
import com.example.super_extra.presentation.components.SuccessDialog
import com.example.super_extra.presentation.menu.MenuScreen
import com.example.super_extra.presentation.product.DetalhesProdutoScreen
import com.example.super_extra.presentation.product.FormularioProdutoScreen
import com.example.super_extra.presentation.product.ProdutosScreen
import com.example.super_extra.presentation.product.ProdutosViewModel
import com.example.super_extra.presentation.splash.SplashScreen
import com.example.super_extra.ui.theme.Super_extraTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Super_extraTheme {
                val produtosViewModel: ProdutosViewModel = viewModel()

                var telaAtual by remember {
                    mutableStateOf("splash")
                }

                var produtoSelecionado by remember {
                    mutableStateOf<Produto?>(null)
                }

                var mostrarSucessoCadastro by remember {
                    mutableStateOf(false)
                }

                when (telaAtual) {
                    "splash" -> {
                        SplashScreen(
                            onSplashFinished = {
                                telaAtual = "menu"
                            }
                        )
                    }

                    "menu" -> {
                        MenuScreen(
                            onProdutosClick = {
                                telaAtual = "produtos"
                            },
                            onClientesClick = {
                                // Tela de clientes
                            },
                            onEnderecosClick = {
                                // Tela de endereços
                            }
                        )
                    }

                    "produtos" -> {
                        ProdutosScreen(
                            viewModel = produtosViewModel,
                            onVoltarClick = {
                                telaAtual = "menu"
                            },
                            onVisualizarClick = { produto ->
                                produtoSelecionado = produto
                                telaAtual = "detalhesProduto"
                            },
                            onNovoProdutoClick = {
                                mostrarSucessoCadastro = false
                                telaAtual = "formularioProduto"
                            }
                        )
                    }

                    "formularioProduto" -> {
                        FormularioProdutoScreen(
                            onVoltarClick = {
                                telaAtual = "produtos"
                            },
                            onCancelarClick = {
                                telaAtual = "produtos"
                            },
                            onSalvarClick = { produto ->
                                produtosViewModel.cadastrarProduto(
                                    produto = produto,
                                    aoFinalizar = {
                                        mostrarSucessoCadastro = true
                                    }
                                )
                            }
                        )

                        if (mostrarSucessoCadastro) {
                            SuccessDialog(
                                titulo = "Sucesso!",
                                mensagem = "Produto cadastrado com sucesso.",
                                textoBotao = "Voltar",
                                onBotaoClick = {
                                    mostrarSucessoCadastro = false
                                    telaAtual = "produtos"
                                }
                            )
                        }
                    }

                    "detalhesProduto" -> {
                        val produto = produtoSelecionado

                        if (produto != null) {
                            DetalhesProdutoScreen(
                                produto = produto,
                                onVoltarClick = {
                                    telaAtual = "produtos"
                                },
                                onEditarClick = {
                                    // Tela de edição
                                },
                                onDeletarClick = {
                                    produtosViewModel.deletarProduto(
                                        id = produto.id,
                                        aoFinalizar = {
                                            produtoSelecionado = null
                                            telaAtual = "produtos"
                                        }
                                    )
                                }
                            )
                        } else {
                            telaAtual = "produtos"
                        }
                    }
                }
            }
        }
    }
}