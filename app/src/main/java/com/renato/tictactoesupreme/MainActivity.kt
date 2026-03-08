package com.renato.tictactoesupreme // Certifique-se de que o pacote está correto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.* // Importa o remember e o mutableStateOf
import androidx.compose.ui.Modifier
import com.renato.tictactoesupreme.logic.GameViewModel
import com.renato.tictactoesupreme.model.GameMode
import com.renato.tictactoesupreme.ui.theme.components.GameScreen
import com.renato.tictactoesupreme.ui.theme.components.MenuScreen
import com.renato.tictactoesupreme.ui.theme.TicTacToeSupremeTheme

class MainActivity : ComponentActivity() {
    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeSupremeTheme {
                // 1. Criamos o estado que guarda qual tela está ativa
                var currentScreen by remember { mutableStateOf(GameMode.MENU) }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 2. O bloco 'when' que você estava procurando
                    when (currentScreen) {
                        GameMode.MENU -> MenuScreen { selectedMode ->
                            // Muda o estado para abrir a tela escolhida
                            currentScreen = selectedMode
                        }

                        GameMode.CLASSIC -> GameScreen(
                            viewModel = viewModel,
                            onBackToMenu = { currentScreen = GameMode.MENU }
                        )

                        GameMode.ADVANCED -> {
                            // Aqui futuramente colocaremos a tela do Modo Supremo
                            // Por enquanto, um botão temporário para voltar
                            androidx.compose.material3.TextButton(onClick = { currentScreen = GameMode.MENU }) {
                                androidx.compose.material3.Text("Modo Avançado (Em construção). Clique para voltar.")
                            }
                        }
                    }
                }
            }
        }
    }
}