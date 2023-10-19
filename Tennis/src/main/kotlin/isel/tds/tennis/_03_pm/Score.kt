package isel.tds.tennis._03_pm

import isel.tds.tennis.Player
import isel.tds.tennis._03_pm.Points.*

enum class Points(val value: Int) {
    LOVE(0), FIFTEEN(15), THIRTY(30), FORTY(40), ADVANTAGE(50), GAME(60);

    fun next(): Points = values()[ordinal + 1]

}

sealed interface Score

private class ByPoints(val pointsA: Points, val pointsB: Points) : Score
private object Deuce : Score
private class Advantage(val player: Player) : Score
private class Game(val winner: Player) : Score

fun initialScore(): Score = ByPoints(LOVE, LOVE)

val Score.placard
    get() = when (this) {
        is ByPoints -> "${pointsA.value} - ${pointsB.value}"
        is Game -> "Game $winner"
        is Deuce -> "Deuce"
        is Advantage -> "Advantage $player"
    }

val Score.isGame get() = this is Game

fun Score.next(win: Player): Score = when (this) {
    is ByPoints -> when {
        (win == Player.A && pointsA == FORTY)
                || (win == Player.B && pointsB == FORTY) -> Game(win)

        (win == Player.B && pointsA == FORTY && pointsB == THIRTY)
                || (win == Player.A && pointsB == FORTY && pointsA == THIRTY) -> Deuce

        win == Player.A -> ByPoints(pointsA.next(), pointsB)
        else -> ByPoints(pointsA, pointsB.next())
    }

    is Game -> error("Game over")
    is Deuce -> Advantage(win)
    is Advantage -> if (win == player) Game(win) else Deuce
}



