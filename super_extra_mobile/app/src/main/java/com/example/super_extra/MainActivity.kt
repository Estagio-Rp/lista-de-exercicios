package com.example.super_extra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.super_extra.domain.model.Cliente
import com.example.super_extra.domain.model.Endereco
import com.example.super_extra.domain.model.Produto
import com.example.super_extra.presentation.address.DetalhesEnderecoScreen
import com.example.super_extra.presentation.address.EnderecosScreen
import com.example.super_extra.presentation.address.EnderecosViewModel
import com.example.super_extra.presentation.address.FormularioEnderecoScreen
import com.example.super_extra.presentation.client.ClientesScreen
import com.example.super_extra.presentation.client.ClientesViewModel
import com.example.super_extra.presentation.client.DetalhesClienteScreen
import com.example.super_extra.presentation.client.FormularioClienteScreen
import com.example.super_extra.presentation.components.AppMessageHost
import com.example.super_extra.presentation.components.AppMessageType
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
                val clientesViewModel: ClientesViewModel = viewModel()
                val enderecosViewModel: EnderecosViewModel = viewModel()

                var telaAtual by remember {
                    mutableStateOf("splash")
                }

                var produtoSelecionado by remember {
                    mutableStateOf<Produto?>(null)
                }

                var clienteSelecionado by remember {
                    mutableStateOf<Cliente?>(null)
                }

                var enderecoSelecionado by remember {
                    mutableStateOf<Endereco?>(null)
                }

                var mensagemSucesso by remember {
                    mutableStateOf("")
                }

                var mensagemErro by remember {
                    mutableStateOf("")
                }

                var telaAposSucesso by remember {
                    mutableStateOf("produtos")
                }

                AnimatedContent(
                    targetState = telaAtual,
                    transitionSpec = {
                        val indoParaFrente = ordemTela(targetState) >= ordemTela(initialState)

                        if (indoParaFrente) {
                            slideInHorizontally(
                                animationSpec = tween(
                                    durationMillis = 320,
                                    easing = FastOutSlowInEasing
                                ),
                                initialOffsetX = { larguraTela ->
                                    larguraTela
                                }
                            ) togetherWith slideOutHorizontally(
                                animationSpec = tween(
                                    durationMillis = 320,
                                    easing = FastOutSlowInEasing
                                ),
                                targetOffsetX = { larguraTela ->
                                    -larguraTela / 3
                                }
                            )
                        } else {
                            slideInHorizontally(
                                animationSpec = tween(
                                    durationMillis = 320,
                                    easing = FastOutSlowInEasing
                                ),
                                initialOffsetX = { larguraTela ->
                                    -larguraTela
                                }
                            ) togetherWith slideOutHorizontally(
                                animationSpec = tween(
                                    durationMillis = 320,
                                    easing = FastOutSlowInEasing
                                ),
                                targetOffsetX = { larguraTela ->
                                    larguraTela / 3
                                }
                            )
                        }
                    },
                    label = "transicao_telas"
                ) { tela ->

                    when (tela) {
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
                                    telaAtual = "clientes"
                                },
                                onEnderecosClick = {
                                    telaAtual = "enderecos"
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
                                            telaAposSucesso = "produtos"
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
                                                telaAposSucesso = "produtos"
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
                                                telaAposSucesso = "produtos"
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

                        "clientes" -> {
                            ClientesScreen(
                                viewModel = clientesViewModel,
                                onVoltarClick = {
                                    telaAtual = "menu"
                                },
                                onVisualizarClick = { cliente ->
                                    clienteSelecionado = cliente
                                    telaAtual = "detalhesCliente"
                                },
                                onNovoClienteClick = {
                                    clienteSelecionado = null
                                    telaAtual = "formularioCadastroCliente"
                                }
                            )
                        }

                        "formularioCadastroCliente" -> {
                            FormularioClienteScreen(
                                clienteParaEditar = null,
                                onVoltarClick = {
                                    telaAtual = "clientes"
                                },
                                onCancelarClick = {
                                    telaAtual = "clientes"
                                },
                                onSalvarClick = { cliente ->
                                    clientesViewModel.cadastrarCliente(
                                        cliente = cliente,
                                        aoFinalizar = {
                                            telaAposSucesso = "clientes"
                                            mensagemSucesso = "Cliente cadastrado com sucesso."
                                        },
                                        aoErro = { erro ->
                                            mensagemErro = erro
                                        }
                                    )
                                }
                            )
                        }

                        "formularioEdicaoCliente" -> {
                            val cliente = clienteSelecionado

                            if (cliente != null) {
                                FormularioClienteScreen(
                                    clienteParaEditar = cliente,
                                    onVoltarClick = {
                                        telaAtual = "detalhesCliente"
                                    },
                                    onCancelarClick = {
                                        telaAtual = "detalhesCliente"
                                    },
                                    onSalvarClick = { clienteAtualizado ->
                                        clientesViewModel.atualizarCliente(
                                            cliente = clienteAtualizado,
                                            aoFinalizar = {
                                                clienteSelecionado = clienteAtualizado
                                                telaAposSucesso = "clientes"
                                                mensagemSucesso = "Cliente atualizado com sucesso."
                                            },
                                            aoErro = { erro ->
                                                mensagemErro = erro
                                            }
                                        )
                                    }
                                )
                            } else {
                                telaAtual = "clientes"
                            }
                        }

                        "detalhesCliente" -> {
                            val cliente = clienteSelecionado

                            if (cliente != null) {
                                DetalhesClienteScreen(
                                    cliente = cliente,
                                    onVoltarClick = {
                                        telaAtual = "clientes"
                                    },
                                    onEditarClick = {
                                        telaAtual = "formularioEdicaoCliente"
                                    },
                                    onDeletarClick = {
                                        clientesViewModel.deletarCliente(
                                            id = cliente.id,
                                            aoFinalizar = {
                                                clienteSelecionado = null
                                                telaAposSucesso = "clientes"
                                                mensagemSucesso = "Cliente removido com sucesso."
                                                telaAtual = "clientes"
                                            },
                                            aoErro = { erro ->
                                                mensagemErro = erro
                                            }
                                        )
                                    }
                                )
                            } else {
                                telaAtual = "clientes"
                            }
                        }

                        "enderecos" -> {
                            EnderecosScreen(
                                viewModel = enderecosViewModel,
                                onVoltarClick = {
                                    telaAtual = "menu"
                                },
                                onVisualizarClick = { endereco ->
                                    enderecoSelecionado = endereco
                                    telaAtual = "detalhesEndereco"
                                },
                                onNovoEnderecoClick = {
                                    enderecoSelecionado = null
                                    telaAtual = "formularioCadastroEndereco"
                                }
                            )
                        }

                        "formularioCadastroEndereco" -> {
                            FormularioEnderecoScreen(
                                enderecoParaEditar = null,
                                viewModel = enderecosViewModel,
                                onVoltarClick = {
                                    telaAtual = "enderecos"
                                },
                                onCancelarClick = {
                                    telaAtual = "enderecos"
                                },
                                onSalvarClick = { endereco ->
                                    enderecosViewModel.cadastrarEndereco(
                                        endereco = endereco,
                                        aoFinalizar = {
                                            telaAposSucesso = "enderecos"
                                            mensagemSucesso = "Endereço cadastrado com sucesso."
                                        },
                                        aoErro = { erro ->
                                            mensagemErro = erro
                                        }
                                    )
                                }
                            )
                        }

                        "formularioEdicaoEndereco" -> {
                            val endereco = enderecoSelecionado

                            if (endereco != null) {
                                FormularioEnderecoScreen(
                                    enderecoParaEditar = endereco,
                                    viewModel = enderecosViewModel,
                                    onVoltarClick = {
                                        telaAtual = "detalhesEndereco"
                                    },
                                    onCancelarClick = {
                                        telaAtual = "detalhesEndereco"
                                    },
                                    onSalvarClick = { enderecoAtualizado ->
                                        enderecosViewModel.atualizarEndereco(
                                            endereco = enderecoAtualizado,
                                            aoFinalizar = {
                                                enderecoSelecionado = enderecoAtualizado
                                                telaAposSucesso = "enderecos"
                                                mensagemSucesso = "Endereço atualizado com sucesso."
                                            },
                                            aoErro = { erro ->
                                                mensagemErro = erro
                                            }
                                        )
                                    }
                                )
                            } else {
                                telaAtual = "enderecos"
                            }
                        }

                        "detalhesEndereco" -> {
                            val endereco = enderecoSelecionado

                            if (endereco != null) {
                                DetalhesEnderecoScreen(
                                    endereco = endereco,
                                    onVoltarClick = {
                                        telaAtual = "enderecos"
                                    },
                                    onEditarClick = {
                                        telaAtual = "formularioEdicaoEndereco"
                                    },
                                    onDeletarClick = {
                                        enderecosViewModel.deletarEndereco(
                                            id = endereco.id,
                                            aoFinalizar = {
                                                enderecoSelecionado = null
                                                telaAposSucesso = "enderecos"
                                                mensagemSucesso = "Endereço removido com sucesso."
                                                telaAtual = "enderecos"
                                            },
                                            aoErro = { erro ->
                                                mensagemErro = erro
                                            }
                                        )
                                    }
                                )
                            } else {
                                telaAtual = "enderecos"
                            }
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
                            telaAtual = telaAposSucesso
                        }
                    )
                }

                if (mensagemErro.isNotBlank()) {
                    AppMessageHost(
                        message = mensagemErro,
                        type = AppMessageType.ERROR,
                        onDismiss = {
                            mensagemErro = ""
                        }
                    )
                }
            }
        }
    }
}

private fun ordemTela(tela: String): Int {
    return when (tela) {
        "splash" -> 0
        "menu" -> 1

        "produtos" -> 2
        "clientes" -> 2
        "enderecos" -> 2

        "detalhesProduto" -> 3
        "detalhesCliente" -> 3
        "detalhesEndereco" -> 3

        "formularioCadastroProduto" -> 3
        "formularioCadastroCliente" -> 3
        "formularioCadastroEndereco" -> 3

        "formularioEdicaoProduto" -> 4
        "formularioEdicaoCliente" -> 4
        "formularioEdicaoEndereco" -> 4

        else -> 0
    }
}