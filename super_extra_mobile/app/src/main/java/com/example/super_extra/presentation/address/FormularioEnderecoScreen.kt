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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.super_extra.domain.model.Endereco
import com.example.super_extra.presentation.components.AppMessageHost
import com.example.super_extra.presentation.components.AppMessageType
import com.example.super_extra.presentation.components.CampoFormularioObrigatorio
import com.example.super_extra.presentation.components.LegendaCampoObrigatorio

private val AzulSuperExtra = Color(0xFF1F238F)
private val FundoTela = Color(0xFFF4F4F7)
private val CinzaTexto = Color(0xFF777777)
private val CinzaBotao = Color(0xFFD4D4D4)
private val PretoIcone = Color(0xFF30323A)
private val VermelhoErro = Color(0xFFD83A42)

@Composable
fun FormularioEnderecoScreen(
    enderecoParaEditar: Endereco? = null,
    viewModel: EnderecosViewModel = viewModel(),
    onVoltarClick: () -> Unit = {},
    onCancelarClick: () -> Unit = {},
    onSalvarClick: (Endereco) -> Unit = {}
) {
    val modoEdicao = enderecoParaEditar != null

    var cep by remember(enderecoParaEditar?.id) {
        mutableStateOf(enderecoParaEditar?.cep ?: "")
    }

    var rua by remember(enderecoParaEditar?.id) {
        mutableStateOf(enderecoParaEditar?.rua ?: "")
    }

    var numero by remember(enderecoParaEditar?.id) {
        mutableStateOf(enderecoParaEditar?.numero?.toString() ?: "")
    }

    var complemento by remember(enderecoParaEditar?.id) {
        mutableStateOf(enderecoParaEditar?.complemento ?: "")
    }

    var bairro by remember(enderecoParaEditar?.id) {
        mutableStateOf(enderecoParaEditar?.bairro ?: "")
    }

    var cidadeId by remember(enderecoParaEditar?.id) {
        mutableIntStateOf(enderecoParaEditar?.cidadeId ?: 0)
    }

    var cidadeEncontrada by remember(enderecoParaEditar?.id) {
        mutableStateOf("")
    }

    var cepConsultado by remember {
        mutableStateOf("")
    }

    var erroCep by remember { mutableStateOf("") }
    var erroRua by remember { mutableStateOf("") }
    var erroNumero by remember { mutableStateOf("") }
    var erroComplemento by remember { mutableStateOf("") }
    var erroBairro by remember { mutableStateOf("") }
    var erroCidade by remember { mutableStateOf("") }

    var mensagemBarra by remember {
        mutableStateOf("")
    }

    var tipoMensagem by remember {
        mutableStateOf(AppMessageType.ERROR)
    }

    val cepNumeros = cep.filter { it.isDigit() }

    LaunchedEffect(cepNumeros) {
        if (cepNumeros.length == 8 && cepNumeros != cepConsultado) {
            cepConsultado = cepNumeros

            viewModel.buscarEnderecoPorCep(
                cep = cepNumeros,
                aoEncontrar = { resultado ->
                    rua = resultado.rua
                    bairro = resultado.bairro

                    if (complemento.isBlank()) {
                        complemento = resultado.complemento
                    }

                    cidadeId = resultado.cidadeId
                    cidadeEncontrada = "${resultado.cidadeNome} - ${resultado.cidadeUf}"

                    erroCep = ""
                    erroRua = ""
                    erroBairro = ""
                    erroCidade = ""
                },
                aoErro = { erro ->
                    cidadeId = 0
                    cidadeEncontrada = ""
                    erroCidade = erro
                    tipoMensagem = AppMessageType.ERROR
                    mensagemBarra = erro
                }
            )
        }
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
            HeaderFormularioEndereco()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .imePadding()
                    .padding(horizontal = 20.dp)
                    .padding(top = 10.dp, bottom = 40.dp)
            ) {
                LinhaNavegacaoFormularioEndereco(
                    onVoltarClick = onVoltarClick,
                    modoEdicao = modoEdicao
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = if (modoEdicao) "Editar Endereço" else "Cadastrar Novo Endereço",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                LegendaCampoObrigatorio()

                Spacer(modifier = Modifier.height(22.dp))

                CampoFormularioObrigatorio(
                    label = "CEP",
                    valor = cep,
                    onValorChange = {
                        cep = it
                        erroCep = ""

                        if (it.filter { caractere -> caractere.isDigit() }.length < 8) {
                            cidadeId = 0
                            cidadeEncontrada = ""
                            erroCidade = ""
                        }
                    },
                    erro = erroCep,
                    keyboardType = KeyboardType.Number
                )

                Spacer(modifier = Modifier.height(12.dp))

                CampoFormularioObrigatorio(
                    label = "Rua",
                    valor = rua,
                    onValorChange = {
                        rua = it
                        erroRua = ""
                    },
                    erro = erroRua
                )

                Spacer(modifier = Modifier.height(12.dp))

                CampoFormularioObrigatorio(
                    label = "Número",
                    valor = numero,
                    onValorChange = {
                        numero = it
                        erroNumero = ""
                    },
                    erro = erroNumero,
                    keyboardType = KeyboardType.Number
                )

                Spacer(modifier = Modifier.height(12.dp))

                CampoFormularioOpcional(
                    label = "Complemento",
                    valor = complemento,
                    onValorChange = {
                        complemento = it
                        erroComplemento = ""
                    },
                    erro = erroComplemento
                )

                Spacer(modifier = Modifier.height(12.dp))

                CampoFormularioObrigatorio(
                    label = "Bairro",
                    valor = bairro,
                    onValorChange = {
                        bairro = it
                        erroBairro = ""
                    },
                    erro = erroBairro
                )

                Spacer(modifier = Modifier.height(12.dp))

                CampoCidadeEncontrada(
                    cidade = cidadeEncontrada,
                    erro = erroCidade
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
                            erroCep = ""
                            erroRua = ""
                            erroNumero = ""
                            erroComplemento = ""
                            erroBairro = ""
                            erroCidade = ""

                            val resultadoValidacao = validarCamposEnderecoDetalhado(
                                cep = cep,
                                rua = rua,
                                numero = numero,
                                complemento = complemento,
                                bairro = bairro,
                                cidadeId = cidadeId
                            )

                            erroCep = resultadoValidacao.erroCep
                            erroRua = resultadoValidacao.erroRua
                            erroNumero = resultadoValidacao.erroNumero
                            erroComplemento = resultadoValidacao.erroComplemento
                            erroBairro = resultadoValidacao.erroBairro
                            erroCidade = resultadoValidacao.erroCidade

                            if (resultadoValidacao.temErro()) {
                                tipoMensagem = AppMessageType.ERROR
                                mensagemBarra = "Verifique os campos informados."
                                return@Button
                            }

                            val endereco = Endereco(
                                id = enderecoParaEditar?.id ?: 0,
                                cep = cep.filter { it.isDigit() },
                                rua = rua.trim(),
                                numero = numero.trim().toInt(),
                                complemento = complemento.trim().ifBlank { null },
                                bairro = bairro.trim(),
                                cidadeId = cidadeId
                            )

                            onSalvarClick(endereco)
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

private data class ResultadoValidacaoEndereco(
    val erroCep: String = "",
    val erroRua: String = "",
    val erroNumero: String = "",
    val erroComplemento: String = "",
    val erroBairro: String = "",
    val erroCidade: String = ""
) {
    fun temErro(): Boolean {
        return erroCep.isNotBlank() ||
                erroRua.isNotBlank() ||
                erroNumero.isNotBlank() ||
                erroComplemento.isNotBlank() ||
                erroBairro.isNotBlank() ||
                erroCidade.isNotBlank()
    }
}

private fun validarCamposEnderecoDetalhado(
    cep: String,
    rua: String,
    numero: String,
    complemento: String,
    bairro: String,
    cidadeId: Int
): ResultadoValidacaoEndereco {
    val cepNumeros = cep.filter { it.isDigit() }
    val ruaLimpa = rua.trim()
    val numeroLimpo = numero.trim()
    val complementoLimpo = complemento.trim()
    val bairroLimpo = bairro.trim()

    val temLetra = Regex("[A-Za-zÀ-ÿ]")

    var erroCep = ""
    var erroRua = ""
    var erroNumero = ""
    var erroComplemento = ""
    var erroBairro = ""
    var erroCidade = ""

    if (cepNumeros.isBlank()) {
        erroCep = "Obrigatório o preenchimento do campo."
    } else if (cepNumeros.length != 8) {
        erroCep = "O CEP deve conter 8 números."
    }

    if (ruaLimpa.isBlank()) {
        erroRua = "Obrigatório o preenchimento do campo."
    } else if (!temLetra.containsMatchIn(ruaLimpa)) {
        erroRua = "A rua não pode ser apenas número."
    }

    if (numeroLimpo.isBlank()) {
        erroNumero = "Obrigatório o preenchimento do campo."
    } else {
        val numeroConvertido = numeroLimpo.toIntOrNull()

        if (numeroConvertido == null) {
            erroNumero = "O número deve ser um valor inteiro."
        } else if (numeroConvertido <= 0) {
            erroNumero = "O número deve ser maior que zero."
        }
    }

    if (complementoLimpo.isNotBlank() && !temLetra.containsMatchIn(complementoLimpo)) {
        erroComplemento = "O complemento não pode ser apenas número."
    }

    if (bairroLimpo.isBlank()) {
        erroBairro = "Obrigatório o preenchimento do campo."
    } else if (!temLetra.containsMatchIn(bairroLimpo)) {
        erroBairro = "O bairro não pode ser apenas número."
    }

    if (cidadeId <= 0) {
        erroCidade = "Digite um CEP válido para preencher a cidade."
    }

    return ResultadoValidacaoEndereco(
        erroCep = erroCep,
        erroRua = erroRua,
        erroNumero = erroNumero,
        erroComplemento = erroComplemento,
        erroBairro = erroBairro,
        erroCidade = erroCidade
    )
}

@Composable
private fun CampoCidadeEncontrada(
    cidade: String,
    erro: String
) {
    val possuiErro = erro.isNotBlank()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Cidade",
            fontSize = 11.sp,
            fontWeight = FontWeight.Black,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = cidade.ifBlank { "Preencha o CEP para buscar a cidade" },
            onValueChange = {},
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = if (cidade.isBlank()) CinzaTexto else Color.Black
            ),
            isError = possuiErro,
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = if (possuiErro) VermelhoErro else Color(0xFFD0D0D0),
                disabledContainerColor = Color.White,
                disabledTextColor = if (cidade.isBlank()) CinzaTexto else Color.Black,
                errorBorderColor = VermelhoErro,
                errorContainerColor = Color.White
            )
        )

        if (possuiErro) {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = erro,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = VermelhoErro
            )
        }
    }
}

@Composable
private fun CampoFormularioOpcional(
    label: String,
    valor: String,
    onValorChange: (String) -> Unit,
    erro: String = ""
) {
    val possuiErro = erro.isNotBlank()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Black,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = valor,
            onValueChange = onValorChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.Black
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            isError = possuiErro,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (possuiErro) VermelhoErro else AzulSuperExtra,
                unfocusedBorderColor = if (possuiErro) VermelhoErro else Color(0xFFD0D0D0),
                errorBorderColor = VermelhoErro,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                errorContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                errorTextColor = Color.Black,
                cursorColor = if (possuiErro) VermelhoErro else AzulSuperExtra,
                errorCursorColor = VermelhoErro
            )
        )

        if (possuiErro) {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = erro,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = VermelhoErro
            )
        }
    }
}

@Composable
private fun HeaderFormularioEndereco() {
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
private fun LinhaNavegacaoFormularioEndereco(
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
                .width(18.dp)
                .height(18.dp)
                .clickable {
                    onVoltarClick()
                }
        )

        Text(
            text = "Endereços",
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