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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.super_extra.R
import com.example.super_extra.domain.model.Produto
import com.example.super_extra.presentation.components.AppMessageHost
import com.example.super_extra.presentation.components.AppMessageType
import com.example.super_extra.presentation.components.CampoFormularioObrigatorio
import com.example.super_extra.presentation.components.LegendaCampoObrigatorio
import java.util.Locale

private val AzulSuperExtra = Color(0xFF1F238F)
private val FundoTela = Color(0xFFF4F4F7)
private val CinzaTexto = Color(0xFF777777)
private val CinzaBotao = Color(0xFFD4D4D4)
private val PretoIcone = Color(0xFF30323A)

@Composable
fun FormularioProdutoScreen(
    produtoParaEditar: Produto? = null,
    onVoltarClick: () -> Unit = {},
    onCancelarClick: () -> Unit = {},
    onSalvarClick: (Produto) -> Unit = {}
) {
    val modoEdicao = produtoParaEditar != null

    var nome by remember(produtoParaEditar?.id) {
        mutableStateOf(produtoParaEditar?.nome ?: "")
    }

    var preco by remember(produtoParaEditar?.id) {
        mutableStateOf(
            produtoParaEditar?.preco?.let {
                String.format(Locale.US, "%.2f", it)
            } ?: ""
        )
    }

    var categoria by remember(produtoParaEditar?.id) {
        mutableStateOf(produtoParaEditar?.categoria ?: "")
    }

    var estoque by remember(produtoParaEditar?.id) {
        mutableStateOf(produtoParaEditar?.estoque?.toString() ?: "")
    }

    var erroNome by remember { mutableStateOf("") }
    var erroPreco by remember { mutableStateOf("") }
    var erroCategoria by remember { mutableStateOf("") }
    var erroEstoque by remember { mutableStateOf("") }

    var mensagemBarra by remember {
        mutableStateOf("")
    }

    var tipoMensagem by remember {
        mutableStateOf(AppMessageType.ERROR)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoTela)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(FundoTela)
        ) {
            HeaderFormularioProduto()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .imePadding()
                    .padding(horizontal = 20.dp)
                    .padding(top = 10.dp, bottom = 40.dp)
            ) {
                LinhaNavegacaoFormulario(
                    onVoltarClick = onVoltarClick,
                    modoEdicao = modoEdicao
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = if (modoEdicao) "Editar Produto" else "Cadastrar Novo Produto",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                LegendaCampoObrigatorio()

                Spacer(modifier = Modifier.height(22.dp))

                CampoFormularioObrigatorio(
                    label = "Nome",
                    valor = nome,
                    onValorChange = {
                        nome = it
                        erroNome = ""
                    },
                    erro = erroNome
                )

                Spacer(modifier = Modifier.height(14.dp))

                CampoFormularioObrigatorio(
                    label = "Preço",
                    valor = preco,
                    onValorChange = {
                        preco = it
                        erroPreco = ""
                    },
                    erro = erroPreco,
                    keyboardType = KeyboardType.Decimal
                )

                Spacer(modifier = Modifier.height(14.dp))

                CampoFormularioObrigatorio(
                    label = "Categoria",
                    valor = categoria,
                    onValorChange = {
                        categoria = it
                        erroCategoria = ""
                    },
                    erro = erroCategoria
                )

                Spacer(modifier = Modifier.height(14.dp))

                CampoFormularioObrigatorio(
                    label = "Estoque",
                    valor = estoque,
                    onValorChange = {
                        estoque = it
                        erroEstoque = ""
                    },
                    erro = erroEstoque,
                    keyboardType = KeyboardType.Number
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = onCancelarClick,
                        modifier = Modifier
                            .width(120.dp)
                            .height(46.dp),
                        shape = RoundedCornerShape(22.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CinzaBotao
                        ),
                        contentPadding = PaddingValues(horizontal = 12.dp)
                    ) {
                        Text(
                            text = "Cancelar",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = CinzaTexto
                        )
                    }

                    Button(
                        onClick = {
                            erroNome = ""
                            erroPreco = ""
                            erroCategoria = ""
                            erroEstoque = ""

                            val resultadoValidacao = validarCamposProdutoDetalhado(
                                nome = nome,
                                preco = preco,
                                categoria = categoria,
                                estoque = estoque
                            )

                            erroNome = resultadoValidacao.erroNome
                            erroPreco = resultadoValidacao.erroPreco
                            erroCategoria = resultadoValidacao.erroCategoria
                            erroEstoque = resultadoValidacao.erroEstoque

                            if (resultadoValidacao.temErro()) {
                                tipoMensagem = AppMessageType.ERROR
                                mensagemBarra = "Verifique os campos obrigatórios."
                                return@Button
                            }

                            val precoConvertido = preco
                                .replace("R$", "")
                                .replace(",", ".")
                                .trim()
                                .toDouble()

                            val estoqueConvertido = estoque
                                .trim()
                                .toInt()

                            val produto = Produto(
                                id = produtoParaEditar?.id ?: 0,
                                nome = nome.trim(),
                                preco = precoConvertido,
                                categoria = categoria.trim(),
                                estoque = estoqueConvertido
                            )

                            onSalvarClick(produto)
                        },
                        modifier = Modifier
                            .width(120.dp)
                            .height(46.dp),
                        shape = RoundedCornerShape(22.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AzulSuperExtra
                        ),
                        contentPadding = PaddingValues(horizontal = 12.dp)
                    ) {
                        Text(
                            text = "Salvar",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(80.dp))
            }
        }

        AppMessageHost(
            message = mensagemBarra,
            type = tipoMensagem,
            topPadding = 104.dp,
            onDismiss = {
                mensagemBarra = ""
            }
        )
    }
}

private data class ResultadoValidacaoProduto(
    val erroNome: String = "",
    val erroPreco: String = "",
    val erroCategoria: String = "",
    val erroEstoque: String = ""
) {
    fun temErro(): Boolean {
        return erroNome.isNotBlank() ||
                erroPreco.isNotBlank() ||
                erroCategoria.isNotBlank() ||
                erroEstoque.isNotBlank()
    }
}

private fun validarCamposProdutoDetalhado(
    nome: String,
    preco: String,
    categoria: String,
    estoque: String
): ResultadoValidacaoProduto {
    val nomeLimpo = nome.trim()
    val precoLimpo = preco
        .replace("R$", "")
        .replace(",", ".")
        .trim()
    val categoriaLimpa = categoria.trim()
    val estoqueLimpo = estoque.trim()

    val temLetra = Regex("[A-Za-zÀ-ÿ]")
    val temNumero = Regex("\\d")

    var erroNome = ""
    var erroPreco = ""
    var erroCategoria = ""
    var erroEstoque = ""

    if (nomeLimpo.isBlank()) {
        erroNome = "Obrigatório o preenchimento do campo."
    } else if (!temLetra.containsMatchIn(nomeLimpo)) {
        erroNome = "O nome do produto não pode ser apenas número."
    }

    if (precoLimpo.isBlank()) {
        erroPreco = "Obrigatório o preenchimento do campo."
    } else {
        val precoConvertido = precoLimpo.toDoubleOrNull()

        if (precoConvertido == null) {
            erroPreco = "O preço deve conter apenas números."
        } else if (precoConvertido <= 0.0) {
            erroPreco = "O preço deve ser maior que zero."
        }
    }

    if (categoriaLimpa.isBlank()) {
        erroCategoria = "Obrigatório o preenchimento do campo."
    } else if (!temLetra.containsMatchIn(categoriaLimpa)) {
        erroCategoria = "A categoria não pode ser apenas número."
    } else if (temNumero.containsMatchIn(categoriaLimpa)) {
        erroCategoria = "A categoria não deve conter números."
    }

    if (estoqueLimpo.isBlank()) {
        erroEstoque = "Obrigatório o preenchimento do campo."
    } else {
        val estoqueConvertido = estoqueLimpo.toIntOrNull()

        if (estoqueConvertido == null) {
            erroEstoque = "O estoque deve conter apenas números inteiros."
        } else if (estoqueConvertido < 0) {
            erroEstoque = "O estoque não pode ser negativo."
        }
    }

    return ResultadoValidacaoProduto(
        erroNome = erroNome,
        erroPreco = erroPreco,
        erroCategoria = erroCategoria,
        erroEstoque = erroEstoque
    )
}

@Composable
private fun HeaderFormularioProduto() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(AzulSuperExtra)
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LogoSuperExtra()

        IconeUsuarioFormulario()
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
private fun IconeUsuarioFormulario() {
    Icon(
        painter = painterResource(id = R.drawable.ic_user),
        contentDescription = "Usuário",
        tint = Color.White,
        modifier = Modifier
            .width(42.dp)
            .height(42.dp)
    )
}

@Composable
private fun LinhaNavegacaoFormulario(
    onVoltarClick: () -> Unit,
    modoEdicao: Boolean
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
            text = "Produtos",
            fontSize = 11.sp,
            color = PretoIcone,
            modifier = Modifier.clickable {
                onVoltarClick()
            }
        )

        Text(
            text = if (modoEdicao) "> Edição" else "> Cadastro",
            fontSize = 11.sp,
            color = CinzaTexto
        )
    }
}