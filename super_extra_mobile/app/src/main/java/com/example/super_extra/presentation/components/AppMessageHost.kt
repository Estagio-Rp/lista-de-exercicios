package com.example.super_extra.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay

enum class AppMessageType {
    SUCCESS,
    ERROR
}

@Composable
fun AppMessageHost(
    message: String,
    type: AppMessageType,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    topPadding: Dp = 24.dp,
    durationMillis: Long = 2600L
) {
    if (message.isNotBlank()) {
        LaunchedEffect(message, type) {
            delay(durationMillis)
            onDismiss()
        }

        Box(
            modifier = modifier
                .fillMaxSize()
                .zIndex(20f)
                .padding(top = topPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically(
                    initialOffsetY = { altura ->
                        -altura
                    }
                ) + fadeIn()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.86f)
                        .heightIn(min = 38.dp)
                        .background(
                            color = when (type) {
                                AppMessageType.SUCCESS -> Color(0xFF0D8F35)
                                AppMessageType.ERROR -> Color(0xFFE13B46)
                            },
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = message,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}