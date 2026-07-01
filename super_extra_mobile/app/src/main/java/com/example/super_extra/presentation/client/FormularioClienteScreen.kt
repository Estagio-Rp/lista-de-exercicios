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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.super_extra.R
import com.example.super_extra.domain.model.Cliente
import com.example.super_extra.presentation.components.AppMessageHost
import com.example.super_extra.presentation.components.AppMessageType
import com.example.super_extra.presentation.components.CampoFormularioObrigatorio
import com.example.super_extra.presentation.components.LegendaCampoObrigatorio

private val AzulSuperExtra = Color(0xFF1F238F)
private val FundoTela = Color(0xFFF4F4F7)
private val CinzaTexto = Color(0xFF777777)
private val CinzaBotao = Color(0xFFD4D4D4)
private val PretoIcone = Color(0xFF30323A)

@Composable
@Preview
fun FormularioClienteScreen(
    clienteParaEditar: Cliente? = null,
    onVoltarClick: () -> Unit = {},
    onCancelarClick: () -> Unit = {},
    onSalvarClick: (Cliente) -> Unit = {}
) {
    val modoEdicao = clienteParaEditar != null

    var nome by remember(clienteParaEditar?.id) {
        mutableStateOf(clienteParaEditar?.nome ?: "")
    }

    var email by remember(clienteParaEditar?.id) {
        mutableStateOf(clienteParaEditar?.email ?: "")
    }

    var cpf by remember(clienteParaEditar?.id) {
        mutableStateOf(clienteParaEditar?.cpf ?: "")
    }

    var telefone by remember(clienteParaEditar?.id) {
        mutableStateOf(clienteParaEditar?.telefone ?: "")
    }

    var enderecoId by remember(clienteParaEditar?.id) {
        mutableStateOf(clienteParaEditar?.enderecoId?.toString() ?: "")
    }

    var erroNome by remember { mutableStateOf("") }
    var erroEmail by remember { mutableStateOf("") }
    var erroCpf by remember { mutableStateOf("") }
    var erroTelefone by remember { mutableStateOf("") }
    var erroEndereco by remember { mutableStateOf("") }

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
            HeaderFormularioCliente()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .imePadding()
                    .padding(horizontal = 20.dp)
                    .padding(top = 10.dp, bottom = 40.dp)
            ) {
                LinhaNavegacaoFormularioCliente(
                    onVoltarClick = onVoltarClick,
                    modoEdicao = modoEdicao
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = if (modoEdicao) "Editar Cliente" else "Cadastrar Novo Cliente",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                LegendaCampoObrigatorio()

                Spacer(modifier = Modifier.height(22.dp))

                CampoFormularioObrigatorio(
                    label = "Nome Completo",
                    valor = nome,
                    onValorChange = {
                        nome = it
                        erroNome = ""
                    },
                    erro = erroNome
                )

                Spacer(modifier = Modifier.height(12.dp))

                CampoFormularioObrigatorio(
                    label = "E-mail",
                    valor = email,
                    onValorChange = {
                        email = it
                        erroEmail = ""
                    },
                    erro = erroEmail,
                    keyboardType = KeyboardType.Email
                )

                Spacer(modifier = Modifier.height(12.dp))

                CampoFormularioObrigatorio(
                    label = "CPF",
                    valor = cpf,
                    onValorChange = {
                        cpf = it
                        erroCpf = ""
                    },
                    erro = erroCpf,
                    keyboardType = KeyboardType.Number
                )

                Spacer(modifier = Modifier.height(12.dp))

                CampoFormularioObrigatorio(
                    label = "Telefone",
                    valor = telefone,
                    onValorChange = {
                        telefone = it
                        erroTelefone = ""
                    },
                    erro = erroTelefone,
                    keyboardType = KeyboardType.Phone
                )

                Spacer(modifier = Modifier.height(12.dp))

                CampoFormularioObrigatorio(
                    label = "ID do endereço",
                    valor = enderecoId,
                    onValorChange = {
                        enderecoId = it
                        erroEndereco = ""
                    },
                    erro = erroEndereco,
                    keyboardType = KeyboardType.Number
                )

                Spacer(modifier = Modifier.height(30.dp))

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
                            erroEmail = ""
                            erroCpf = ""
                            erroTelefone = ""
                            erroEndereco = ""

                            val resultadoValidacao = validarCamposClienteDetalhado(
                                nome = nome,
                                email = email,
                                cpf = cpf,
                                telefone = telefone,
                                enderecoId = enderecoId
                            )

                            erroNome = resultadoValidacao.erroNome
                            erroEmail = resultadoValidacao.erroEmail
                            erroCpf = resultadoValidacao.erroCpf
                            erroTelefone = resultadoValidacao.erroTelefone
                            erroEndereco = resultadoValidacao.erroEndereco

                            if (resultadoValidacao.temErro()) {
                                tipoMensagem = AppMessageType.ERROR
                                mensagemBarra = "Verifique os campos obrigatórios."
                                return@Button
                            }

                            val cliente = Cliente(
                                id = clienteParaEditar?.id ?: 0,
                                nome = nome.trim(),
                                email = email.trim(),
                                cpf = cpf.filter { it.isDigit() },
                                telefone = telefone.filter { it.isDigit() },
                                dataCadastro = clienteParaEditar?.dataCadastro,
                                enderecoId = enderecoId.trim().toInt()
                            )

                            onSalvarClick(cliente)
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

private data class ResultadoValidacaoCliente(
    val erroNome: String = "",
    val erroEmail: String = "",
    val erroCpf: String = "",
    val erroTelefone: String = "",
    val erroEndereco: String = ""
) {
    fun temErro(): Boolean {
        return erroNome.isNotBlank() ||
                erroEmail.isNotBlank() ||
                erroCpf.isNotBlank() ||
                erroTelefone.isNotBlank() ||
                erroEndereco.isNotBlank()
    }
}

private fun validarCamposClienteDetalhado(
    nome: String,
    email: String,
    cpf: String,
    telefone: String,
    enderecoId: String
): ResultadoValidacaoCliente {
    val nomeLimpo = nome.trim()
    val emailLimpo = email.trim()
    val cpfNumeros = cpf.filter { it.isDigit() }
    val telefoneNumeros = telefone.filter { it.isDigit() }
    val enderecoLimpo = enderecoId.trim()

    val temLetra = Regex("[A-Za-zÀ-ÿ]")
    val emailValido = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")

    var erroNome = ""
    var erroEmail = ""
    var erroCpf = ""
    var erroTelefone = ""
    var erroEndereco = ""

    if (nomeLimpo.isBlank()) {
        erroNome = "Obrigatório o preenchimento do campo."
    } else if (!temLetra.containsMatchIn(nomeLimpo)) {
        erroNome = "O nome não pode ser apenas número."
    } else if (nomeLimpo.length < 3) {
        erroNome = "O nome deve ter pelo menos 3 caracteres."
    }

    if (emailLimpo.isBlank()) {
        erroEmail = "Obrigatório o preenchimento do campo."
    } else if (!emailValido.matches(emailLimpo)) {
        erroEmail = "Por favor, insira um e-mail válido."
    }

    if (cpfNumeros.isBlank()) {
        erroCpf = "Obrigatório o preenchimento do campo."
    } else if (cpfNumeros.length != 11) {
        erroCpf = "O CPF deve conter 11 números."
    } else if (!cpfValido(cpfNumeros)) {
        erroCpf = "CPF inválido."
    }

    if (telefoneNumeros.isBlank()) {
        erroTelefone = "Obrigatório o preenchimento do campo."
    } else if (telefoneNumeros.length !in 10..11) {
        erroTelefone = "O telefone deve conter 10 ou 11 números."
    }

    if (enderecoLimpo.isBlank()) {
        erroEndereco = "Obrigatório o preenchimento do campo."
    } else {
        val enderecoConvertido = enderecoLimpo.toIntOrNull()

        if (enderecoConvertido == null) {
            erroEndereco = "O endereço deve ser informado como número."
        } else if (enderecoConvertido <= 0) {
            erroEndereco = "O ID do endereço deve ser maior que zero."
        }
    }

    return ResultadoValidacaoCliente(
        erroNome = erroNome,
        erroEmail = erroEmail,
        erroCpf = erroCpf,
        erroTelefone = erroTelefone,
        erroEndereco = erroEndereco
    )
}

private fun cpfValido(cpf: String): Boolean {
    if (cpf.length != 11 || cpf.all { it == cpf.first() }) {
        return false
    }

    val numeros = cpf.map { it.digitToInt() }

    val primeiroDigito = calcularDigitoCpf(
        numeros = numeros,
        quantidade = 9,
        pesoInicial = 10
    )

    val segundoDigito = calcularDigitoCpf(
        numeros = numeros,
        quantidade = 10,
        pesoInicial = 11
    )

    return numeros[9] == primeiroDigito && numeros[10] == segundoDigito
}

private fun calcularDigitoCpf(
    numeros: List<Int>,
    quantidade: Int,
    pesoInicial: Int
): Int {
    val soma = (0 until quantidade).sumOf { indice ->
        numeros[indice] * (pesoInicial - indice)
    }

    val resto = soma % 11

    return if (resto < 2) {
        0
    } else {
        11 - resto
    }
}

@Composable
private fun HeaderFormularioCliente() {
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

        IconeUsuarioHeader()
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
private fun IconeUsuarioHeader() {
    Icon(
        painter = painterResource(id = R.drawable.ic_user),
        contentDescription = "Usuário",
        tint = Color.White,
        modifier = Modifier
            .width(38.dp)
            .height(38.dp)
    )
}

@Composable
private fun LinhaNavegacaoFormularioCliente(
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
            text = "Clientes",
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