package com.renato.tictactoesupreme.model

// Representa o estado de uma célula
enum class Player { X, O, NONE }
enum class GameMode { MENU, CLASSIC, ADVANCED }
// Representa o estado do jogo
data class GameState(
    val board: List<Player> = List(9) { Player.NONE },
    val currentPlayer: Player = Player.X,
    val winner: Player? = null,
    val isDraw: Boolean = false,

    // Novos campos para o placar:
    val xWins: Int = 0,
    val oWins: Int = 0,
    val draws: Int = 0
)