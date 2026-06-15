package com.example.super_extra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.super_extra.presentation.menu.MenuScreen
import com.example.super_extra.presentation.product.ProdutosScreen
import com.example.super_extra.presentation.splash.SplashScreen
import com.example.super_extra.ui.theme.Super_extraTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Super_extraTheme {
                var telaAtual by remember {
                    mutableStateOf("splash")
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
                                //Tela de clientes
                            },
                            onEnderecosClick = {
                                //Tela de endereços
                            }
                        )
                    }

                    "produtos" -> {
                        ProdutosScreen(
                            onVoltarClick = {
                                telaAtual = "menu"
                            }
                        )
                    }
                }
            }
        }
    }
}