package com.example.super_extra.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val AzulCadastro = Color(0xFF1F238F)
private val FundoCadastro = Color(0xFFF4F4F7)
private val CinzaCadastro = Color(0xFF777777)
private val VermelhoCadastro = Color(0xFFD83A42)

@Composable
fun CadastroUsuarioScreen(
    viewModel: AuthViewModel,
    onVoltarClick: () -> Unit = {},
    onCadastroSucesso: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    var nome by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var senha by remember {
        mutableStateOf("")
    }

    var confirmarSenha by remember {
        mutableStateOf("")
    }

    var mostrarSenha by remember {
        mutableStateOf(false)
    }

    var mostrarConfirmacao by remember {
        mutableStateOf(false)
    }

    var erroNome by remember {
        mutableStateOf("")
    }

    var erroEmail by remember {
        mutableStateOf("")
    }

    var erroSenha by remember {
        mutableStateOf("")
    }

    var erroConfirmacao by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        viewModel.limparMensagens()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoCadastro)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(horizontal = 28.dp)
                .padding(top = 26.dp, bottom = 50.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Voltar",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            viewModel.limparMensagens()
                            onVoltarClick()
                        }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Voltar",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.clickable {
                        viewModel.limparMensagens()
                        onVoltarClick()
                    }
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LogoCadastro()

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Criar conta",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Preencha os dados para realizar seu cadastro",
                    fontSize = 13.sp,
                    color = CinzaCadastro,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            CampoCadastro(
                label = "Nome completo",
                valor = nome,
                placeholder = "Digite seu nome",
                erro = erroNome,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null,
                        tint = AzulCadastro
                    )
                },
                onValorChange = {
                    nome = it
                    erroNome = ""
                    viewModel.limparMensagemErro()
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            CampoCadastro(
                label = "E-mail",
                valor = email,
                placeholder = "Digite seu e-mail",
                erro = erroEmail,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Mail,
                        contentDescription = null,
                        tint = AzulCadastro
                    )
                },
                onValorChange = {
                    email = it
                    erroEmail = ""
                    viewModel.limparMensagemErro()
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            CampoSenhaCadastro(
                label = "Senha",
                valor = senha,
                placeholder = "Digite sua senha",
                erro = erroSenha,
                mostrarSenha = mostrarSenha,
                onMostrarSenhaChange = {
                    mostrarSenha = !mostrarSenha
                },
                onValorChange = {
                    senha = it
                    erroSenha = ""
                    viewModel.limparMensagemErro()
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            CampoSenhaCadastro(
                label = "Confirmar senha",
                valor = confirmarSenha,
                placeholder = "Digite novamente sua senha",
                erro = erroConfirmacao,
                mostrarSenha = mostrarConfirmacao,
                onMostrarSenhaChange = {
                    mostrarConfirmacao = !mostrarConfirmacao
                },
                onValorChange = {
                    confirmarSenha = it
                    erroConfirmacao = ""
                    viewModel.limparMensagemErro()
                }
            )

            if (state.mensagemErro.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = state.mensagemErro,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = VermelhoCadastro,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(26.dp))

            Button(
                onClick = {
                    erroNome = ""
                    erroEmail = ""
                    erroSenha = ""
                    erroConfirmacao = ""

                    if (nome.isBlank()) {
                        erroNome = "Informe o nome."
                    }

                    if (email.isBlank()) {
                        erroEmail = "Informe o e-mail."
                    }

                    if (senha.isBlank()) {
                        erroSenha = "Informe a senha."
                    } else if (senha.length < 6) {
                        erroSenha = "A senha deve possuir pelo menos 6 caracteres."
                    }

                    if (confirmarSenha.isBlank()) {
                        erroConfirmacao = "Confirme a senha."
                    } else if (senha != confirmarSenha) {
                        erroConfirmacao = "As senhas não correspondem."
                    }

                    val possuiErro = erroNome.isNotBlank() ||
                            erroEmail.isNotBlank() ||
                            erroSenha.isNotBlank() ||
                            erroConfirmacao.isNotBlank()

                    if (possuiErro) {
                        return@Button
                    }

                    viewModel.cadastrar(
                        nome = nome,
                        email = email,
                        senha = senha,
                        confirmarSenha = confirmarSenha,
                        aoFinalizar = onCadastroSucesso
                    )
                },
                enabled = !state.carregando,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AzulCadastro,
                    disabledContainerColor = AzulCadastro.copy(alpha = 0.6f)
                )
            ) {
                if (state.carregando) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Cadastrar",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Já possui uma conta?",
                    fontSize = 13.sp,
                    color = CinzaCadastro
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = "Entrar",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = AzulCadastro,
                    modifier = Modifier.clickable {
                        viewModel.limparMensagens()
                        onVoltarClick()
                    }
                )
            }
        }
    }
}

@Composable
private fun LogoCadastro() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            BarraLogoCadastro(altura = 42)

            Spacer(modifier = Modifier.width(8.dp))

            BarraLogoCadastro(altura = 42)

            Spacer(modifier = Modifier.width(8.dp))

            BarraLogoCadastro(altura = 27)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "SUPER EXTRA",
            fontSize = 12.sp,
            fontWeight = FontWeight.Black,
            color = AzulCadastro,
            letterSpacing = 1.sp
        )
    }
}

@Composable
private fun BarraLogoCadastro(
    altura: Int
) {
    Box(
        modifier = Modifier
            .width(12.dp)
            .height(altura.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(AzulCadastro)
    )
}

@Composable
private fun CampoCadastro(
    label: String,
    valor: String,
    placeholder: String,
    erro: String,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    leadingIcon: @Composable () -> Unit,
    onValorChange: (String) -> Unit
) {
    val possuiErro = erro.isNotBlank()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
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
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 13.sp,
                    color = CinzaCadastro
                )
            },
            leadingIcon = leadingIcon,
            shape = RoundedCornerShape(10.dp),
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.Black
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = if (keyboardType == KeyboardType.Text) {
                    KeyboardCapitalization.Words
                } else {
                    KeyboardCapitalization.None
                },
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            isError = possuiErro,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AzulCadastro,
                unfocusedBorderColor = Color(0xFFD0D0D0),
                errorBorderColor = VermelhoCadastro,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                errorContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = AzulCadastro
            )
        )

        if (possuiErro) {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = erro,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = VermelhoCadastro
            )
        }
    }
}

@Composable
private fun CampoSenhaCadastro(
    label: String,
    valor: String,
    placeholder: String,
    erro: String,
    mostrarSenha: Boolean,
    onMostrarSenhaChange: () -> Unit,
    onValorChange: (String) -> Unit
) {
    val possuiErro = erro.isNotBlank()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
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
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 13.sp,
                    color = CinzaCadastro
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = null,
                    tint = if (possuiErro) {
                        VermelhoCadastro
                    } else {
                        AzulCadastro
                    }
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = onMostrarSenhaChange
                ) {
                    Icon(
                        imageVector = if (mostrarSenha) {
                            Icons.Outlined.VisibilityOff
                        } else {
                            Icons.Outlined.Visibility
                        },
                        contentDescription = if (mostrarSenha) {
                            "Ocultar senha"
                        } else {
                            "Mostrar senha"
                        },
                        tint = CinzaCadastro
                    )
                }
            },
            visualTransformation = if (mostrarSenha) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            shape = RoundedCornerShape(10.dp),
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.Black
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            isError = possuiErro,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AzulCadastro,
                unfocusedBorderColor = Color(0xFFD0D0D0),
                errorBorderColor = VermelhoCadastro,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                errorContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = AzulCadastro
            )
        )

        if (possuiErro) {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = erro,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = VermelhoCadastro
            )
        }
    }
}