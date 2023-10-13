package isel.tds.tennis._01_singleclass

import isel.tds.tennis.Player
import isel.tds.tennis._01_singleclass.Points.*

enum class Points(val value: Int) {
    LOVE(0), FIFTEEN(15), THIRTY(30), FORTY(40), ADVANTAGE(50), GAME(60);

    fun next(): Points = values()[ordinal + 1]

}

fun initialScore() = Score(LOVE, LOVE)

class Score(val pointsA: Points, val pointsB: Points) {
    fun next(winner: Player): Score = when {
        winner == Player.A && pointsA == FORTY && pointsB == ADVANTAGE -> Score(FORTY, FORTY)
        winner == Player.B && pointsB == FORTY && pointsA == ADVANTAGE -> Score(FORTY, FORTY)
        winner == Player.A && pointsA == FORTY && pointsB != FORTY -> Score(GAME, pointsB)
        winner == Player.B && pointsB == FORTY && pointsA != FORTY -> Score(pointsA, GAME)
        else ->
            if (winner == Player.A) Score(pointsA.next(), pointsB)
            else Score(pointsA, pointsB.next())
    }

    val isGame: Boolean get() = pointsA == GAME || pointsB == GAME

    val placard: String
        get() = when {
            pointsA == FORTY && pointsB == FORTY -> "Deuce"
            pointsA == ADVANTAGE -> "Advantage A"
            pointsB == ADVANTAGE -> "Advantage B"
            pointsA == GAME -> "Game A"
            pointsB == GAME -> "Game B"
            else -> "${pointsA.value} - ${pointsB.value}"
        }
}



