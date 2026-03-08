package com.renato.tictactoesupreme.logic
import com.renato.tictactoesupreme.model.GameState
import com.renato.tictactoesupreme.model.Player
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class GameViewModel : ViewModel() {
    // O estado do jogo que a ui vai observar
    var state by mutableStateOf(GameState())
        private set

    fun onCellClick(index: Int){
        // Se ja tem dono ou acabou nao faça nada
        if (state.board[index] != Player.NONE || state.winner != null) return

        // Atualiza a jogada
        val newBoard = state.board.toMutableList()
        newBoard[index] = state.currentPlayer

        val winner = calculateWinner(newBoard)

        // O jogo é empate se não houver vencedor E não houver mais espaços vazios (NONE)
        val isDraw = winner == null && !newBoard.contains(Player.NONE)

        state = state.copy(
            board = newBoard,
            currentPlayer = if (state.currentPlayer == Player.X) Player.O else Player.X,
            winner = winner,
            isDraw = isDraw,
            // Atualiza os contadores baseados no resultado:
            xWins = if (winner == Player.X) state.xWins + 1 else state.xWins,
            oWins = if (winner == Player.O) state.oWins + 1 else state.oWins,
            draws = if (isDraw) state.draws + 1 else state.draws
        )
    }

    private fun calculateWinner(board: List<Player>): Player? {
        val lines = listOf(
            listOf(0,1,2), listOf(3,4,5), listOf(6,7,8), // horizontais
            listOf(0,3,6), listOf(1,4,7), listOf(2,5,8), // verticais
            listOf(0,4,8), listOf(2,4,6)                 // diagonais
        )
        for (line in lines) {
            if (board[line[0]] != Player.NONE &&
                board[line[0]] == board[line[1]] &&
                board[line[0]] == board[line[2]]) {
                return board[line[0]]
            }
        }
        return null
    }

    fun resetGame() {
        state = state.copy(
            board = List(9) { Player.NONE },
            currentPlayer = Player.X,
            winner = null,
            isDraw = false
            // Note que NÃO resetamos xWins, oWins e draws aqui!
        )
    }
}





























