package com.example.super_extra.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape

private val AzulSuperExtra = Color(0xFF1F238F)
private val VermelhoErro = Color(0xFFD83A42)

@Composable
fun CampoFormularioObrigatorio(
    label: String,
    valor: String,
    onValorChange: (String) -> Unit,
    erro: String = "",
    keyboardType: KeyboardType = KeyboardType.Text
) {
    val possuiErro = erro.isNotBlank()

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

            Text(
                text = "*",
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                color = VermelhoErro
            )
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
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.Black
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
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