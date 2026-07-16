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
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Mail
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val AzulSuperExtra = Color(0xFF1F238F)
private val FundoLogin = Color(0xFFF4F4F7)
private val CinzaTextoLogin = Color(0xFF777777)
private val VermelhoErroLogin = Color(0xFFD83A42)

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onLoginSucesso: () -> Unit = {},
    onCriarContaClick: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    var email by remember {
        mutableStateOf("")
    }

    var senha by remember {
        mutableStateOf("")
    }

    var mostrarSenha by remember {
        mutableStateOf(false)
    }

    var erroEmail by remember {
        mutableStateOf("")
    }

    var erroSenha by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        viewModel.limparMensagens()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoLogin)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(horizontal = 28.dp)
                .padding(top = 70.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LogoLogin()

            Spacer(modifier = Modifier.height(42.dp))

            Text(
                text = "Bem-vindo!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Entre com seus dados para continuar",
                fontSize = 14.sp,
                color = CinzaTextoLogin,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(36.dp))

            CampoEmailLogin(
                valor = email,
                erro = erroEmail,
                onValorChange = {
                    email = it
                    erroEmail = ""
                    viewModel.limparMensagemErro()
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            CampoSenhaLogin(
                valor = senha,
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

            if (state.mensagemErro.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = state.mensagemErro,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = VermelhoErroLogin,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = {
                    erroEmail = ""
                    erroSenha = ""

                    if (email.isBlank()) {
                        erroEmail = "Informe o e-mail."
                    }

                    if (senha.isBlank()) {
                        erroSenha = "Informe a senha."
                    }

                    if (erroEmail.isNotBlank() || erroSenha.isNotBlank()) {
                        return@Button
                    }

                    viewModel.autenticar(
                        email = email,
                        senha = senha,
                        aoFinalizar = onLoginSucesso
                    )
                },
                enabled = !state.carregando,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AzulSuperExtra,
                    disabledContainerColor = AzulSuperExtra.copy(alpha = 0.6f)
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
                        text = "Entrar",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Ainda não possui uma conta?",
                    fontSize = 13.sp,
                    color = CinzaTextoLogin
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = "Cadastre-se",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = AzulSuperExtra,
                    modifier = Modifier.clickable {
                        viewModel.limparMensagens()
                        onCriarContaClick()
                    }
                )
            }
        }
    }
}

@Composable
private fun LogoLogin() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            BarraLogoLogin(
                altura = 54
            )

            Spacer(modifier = Modifier.width(10.dp))

            BarraLogoLogin(
                altura = 54
            )

            Spacer(modifier = Modifier.width(10.dp))

            BarraLogoLogin(
                altura = 34
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "SUPER EXTRA",
            fontSize = 15.sp,
            fontWeight = FontWeight.Black,
            color = AzulSuperExtra,
            letterSpacing = 1.sp
        )
    }
}

@Composable
private fun BarraLogoLogin(
    altura: Int
) {
    Box(
        modifier = Modifier
            .width(15.dp)
            .height(altura.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(AzulSuperExtra)
    )
}

@Composable
private fun CampoEmailLogin(
    valor: String,
    erro: String,
    onValorChange: (String) -> Unit
) {
    val possuiErro = erro.isNotBlank()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "E-mail",
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
                    text = "Digite seu e-mail",
                    fontSize = 13.sp,
                    color = CinzaTextoLogin
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Mail,
                    contentDescription = null,
                    tint = if (possuiErro) {
                        VermelhoErroLogin
                    } else {
                        AzulSuperExtra
                    }
                )
            },
            shape = RoundedCornerShape(10.dp),
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.Black
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            isError = possuiErro,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AzulSuperExtra,
                unfocusedBorderColor = Color(0xFFD0D0D0),
                errorBorderColor = VermelhoErroLogin,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                errorContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = AzulSuperExtra
            )
        )

        if (possuiErro) {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = erro,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = VermelhoErroLogin
            )
        }
    }
}

@Composable
private fun CampoSenhaLogin(
    valor: String,
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
            text = "Senha",
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
                    text = "Digite sua senha",
                    fontSize = 13.sp,
                    color = CinzaTextoLogin
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = null,
                    tint = if (possuiErro) {
                        VermelhoErroLogin
                    } else {
                        AzulSuperExtra
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
                        tint = CinzaTextoLogin
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
                imeAction = ImeAction.Done
            ),
            isError = possuiErro,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AzulSuperExtra,
                unfocusedBorderColor = Color(0xFFD0D0D0),
                errorBorderColor = VermelhoErroLogin,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                errorContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = AzulSuperExtra
            )
        )

        if (possuiErro) {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = erro,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = VermelhoErroLogin
            )
        }
    }
}