package com.renato.tictactoesupreme.ui.theme.components



import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.renato.tictactoesupreme.logic.GameViewModel
import com.renato.tictactoesupreme.model.Player
import androidx.compose.ui.draw.drawBehind     // Para o modifier .drawBehind
import androidx.compose.ui.geometry.Offset    // Para definir o início e fim das linhas



@Composable
fun GameScreen(viewModel: GameViewModel, onBackToMenu: () -> Unit) {
    val state = viewModel.state // Pegamos o estado atual do jogo

    Column(
        modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Adiciona uma barra superior para o botão voltar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ){
            // Adicione um botão ou um ícone para voltar
            androidx.compose.material3.TextButton(onClick = onBackToMenu) {
                Text("← Voltar ao Menu", fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 1. Placar
        Scoreboard(state.xWins, state.oWins, state.draws)

        Spacer(modifier = Modifier.height(32.dp))
        // --- Cabeçalho: Status do Jogo ---

        Text(
            text = when {
                state.winner != null -> "Vencedor: ${state.winner}"
                state.isDraw -> "Empate!"
                else -> "Vez do Jogador: ${state.currentPlayer}"
            },
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // --- Tabuleiro 3x3 ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f) // Mantém o tabuleiro quadrado

        ) {
            // 3. Tabuleiro (Sem a borda do Box externo agora!)
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                userScrollEnabled = false
            ) {
                items(9) { index ->
                    GameCell(index, state.board[index]) {
                        viewModel.onCellClick(index)
                    }
                }
            }
        }

        // --- Botão de Reiniciar ---
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { viewModel.resetGame() }) {
            Text("Reiniciar Jogo", fontSize = 18.sp)
        }
    }
}

@Composable
fun Scoreboard(xWins: Int, oWins: Int, draws: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ScoreItem("Jogador X", xWins, Color.Blue)
        ScoreItem("Empates", draws, Color.Gray)
        ScoreItem("Jogador O", oWins, Color.Red)
    }
}

@Composable
fun ScoreItem(label: String, score: Int, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontSize = 14.sp, color = color)
        Text(text = score.toString(), fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun GameCell(index: Int, player: Player, onClick: () -> Unit) {
    // Usamos o onSurface para que a cor da linha mude entre modo claro/escuro
    val lineColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable { onClick() }
            .drawBehind {
                val strokeWidthPx = 4.dp.toPx()
                // Linha Vertical (Direita) - apenas para colunas 0 e 1
                if (index % 3 != 2) {
                    drawLine(
                        color = lineColor,
                        start = Offset(size.width, 0f),
                        end = Offset(size.width, size.height),
                        strokeWidth = strokeWidthPx
                    )
                }
                // Linha Horizontal (Baixo) - apenas para linhas 0 e 1
                if (index < 6) {
                    drawLine(
                        color = lineColor,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = strokeWidthPx
                    )
                }
            },
        contentAlignment = Alignment.Center
    ) {
        // --- ANIMAÇÃO DE POP ---
        androidx.compose.animation.AnimatedContent(
            targetState = player,
            transitionSpec = {
                // Faz o símbolo crescer de 0% a 100% com um leve "pulo" (Overshoot)
                (androidx.compose.animation.scaleIn(
                    animationSpec = androidx.compose.animation.core.spring(
                        dampingRatio = androidx.compose.animation.core.Spring.DampingRatioMediumBouncy,
                        stiffness = androidx.compose.animation.core.Spring.StiffnessLow
                    )
                ) + androidx.compose.animation.fadeIn()).togetherWith(
                    androidx.compose.animation.fadeOut()
                )
            },
            label = "SymbolAnimation"
        ) { targetPlayer ->
            if (targetPlayer != Player.NONE) {
                Text(
                    text = targetPlayer.name,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (targetPlayer == Player.X) Color(0xFF2196F3) else Color(0xFFF44336)
                )
            }
        }

    }
}