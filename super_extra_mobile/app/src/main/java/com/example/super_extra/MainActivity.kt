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

                var mensagemSucesso by remember {
                    mutableStateOf("")
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
                                produtoSelecionado = null
                                telaAtual = "formularioCadastroProduto"
                            }
                        )
                    }

                    "formularioCadastroProduto" -> {
                        FormularioProdutoScreen(
                            produtoParaEditar = null,
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
                                        mensagemSucesso = "Produto cadastrado com sucesso."
                                    }
                                )
                            }
                        )
                    }

                    "formularioEdicaoProduto" -> {
                        val produto = produtoSelecionado

                        if (produto != null) {
                            FormularioProdutoScreen(
                                produtoParaEditar = produto,
                                onVoltarClick = {
                                    telaAtual = "detalhesProduto"
                                },
                                onCancelarClick = {
                                    telaAtual = "detalhesProduto"
                                },
                                onSalvarClick = { produtoAtualizado ->
                                    produtosViewModel.atualizarProduto(
                                        produto = produtoAtualizado,
                                        aoFinalizar = {
                                            produtoSelecionado = produtoAtualizado
                                            mensagemSucesso = "Produto atualizado com sucesso."
                                        }
                                    )
                                }
                            )
                        } else {
                            telaAtual = "produtos"
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
                                    telaAtual = "formularioEdicaoProduto"
                                },
                                onDeletarClick = {
                                    produtosViewModel.deletarProduto(
                                        id = produto.id,
                                        aoFinalizar = {
                                            produtoSelecionado = null
                                            mensagemSucesso = "Produto removido com sucesso."
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

                if (mensagemSucesso.isNotBlank()) {
                    SuccessDialog(
                        titulo = "Sucesso!",
                        mensagem = mensagemSucesso,
                        textoBotao = "Voltar",
                        onBotaoClick = {
                            mensagemSucesso = ""
                            telaAtual = "produtos"
                        }
                    )
                }
            }
        }
    }
}