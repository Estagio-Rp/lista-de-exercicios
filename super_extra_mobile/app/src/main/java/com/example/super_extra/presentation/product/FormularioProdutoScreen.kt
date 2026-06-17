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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.super_extra.R
import com.example.super_extra.domain.model.Produto
import java.util.Locale

private val AzulSuperExtra = Color(0xFF1F238F)
private val FundoTela = Color(0xFFF4F4F7)
private val CinzaTexto = Color(0xFF777777)
private val CinzaBotao = Color(0xFFD4D4D4)
private val PretoIcone = Color(0xFF30323A)
private val VermelhoErro = Color(0xFFD83A42)

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

    var mensagemErro by remember {
        mutableStateOf("")
    }

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

            Spacer(modifier = Modifier.height(26.dp))

            CampoFormulario(
                label = "Nome",
                obrigatorio = true,
                valor = nome,
                onValorChange = {
                    nome = it
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            CampoFormulario(
                label = "Preço",
                obrigatorio = true,
                valor = preco,
                onValorChange = {
                    preco = it
                },
                keyboardType = KeyboardType.Decimal
            )

            Spacer(modifier = Modifier.height(14.dp))

            CampoFormulario(
                label = "Categoria",
                obrigatorio = true,
                valor = categoria,
                onValorChange = {
                    categoria = it
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            CampoFormulario(
                label = "Estoque",
                obrigatorio = true,
                valor = estoque,
                onValorChange = {
                    estoque = it
                },
                keyboardType = KeyboardType.Number
            )

            if (mensagemErro.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = mensagemErro,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = VermelhoErro
                )
            }

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
                        val precoConvertido = preco
                            .replace("R$", "")
                            .replace(",", ".")
                            .trim()
                            .toDoubleOrNull()

                        val estoqueConvertido = estoque
                            .trim()
                            .toIntOrNull()

                        if (nome.isBlank() || preco.isBlank() || categoria.isBlank() || estoque.isBlank()) {
                            mensagemErro = "Preencha todos os campos obrigatórios."
                            return@Button
                        }

                        if (precoConvertido == null) {
                            mensagemErro = "Informe um preço válido."
                            return@Button
                        }

                        if (estoqueConvertido == null) {
                            mensagemErro = "Informe um estoque válido."
                            return@Button
                        }

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

@Composable
private fun CampoFormulario(
    label: String,
    obrigatorio: Boolean,
    valor: String,
    onValorChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black
            )

            if (obrigatorio) {
                Text(
                    text = "*",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    color = VermelhoErro
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = valor,
            onValueChange = onValorChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 14.sp,
                color = Color.Black
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AzulSuperExtra,
                unfocusedBorderColor = Color(0xFFD0D0D0),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = AzulSuperExtra
            )
        )
    }
}
