package isel.tds.tennis._02_oo

import isel.tds.tennis.Player
import isel.tds.tennis._02_oo.Points.*

enum class Points(val value: Int) {
    LOVE(0), FIFTEEN(15), THIRTY(30), FORTY(40), ADVANTAGE(50), GAME(60);

    fun next(): Points = values()[ordinal + 1]

}

fun initialScore(): Score = ByPoints(LOVE, LOVE)

interface Score {
    fun next(winner: Player): Score
    val isGame: Boolean get() = false
    val placard: String
}

private class ByPoints(val pointsA: Points, val pointsB: Points) : Score {
    override fun next(winner: Player): Score = when {
        winner == Player.A && pointsA == FORTY && pointsB != FORTY -> Game(Player.A)
        winner == Player.B && pointsB == FORTY && pointsA != FORTY -> Game(Player.B)
        winner == Player.A && pointsA == THIRTY && pointsB == FORTY -> Deuce()
        winner == Player.B && pointsB == THIRTY && pointsA == FORTY -> Deuce()
        else ->
            if (winner == Player.A) ByPoints(pointsA.next(), pointsB)
            else ByPoints(pointsA, pointsB.next())
    }

    override val placard: String
        get() = "${pointsA.value} - ${pointsB.value}"

}

private class Deuce : Score {
    override fun next(winner: Player): Score = when {
        winner == Player.A -> Advantage(Player.A)
        else -> Advantage(Player.B)
    }

    override val placard: String get() = "Deuce"

}

private class Advantage(val player: Player) : Score {
    override fun next(winner: Player): Score = when {
        winner == player -> Game(player)
        else -> Deuce()
    }

    override val placard: String get() = "Advantage ${player.name}"

}

private class Game(val winner: Player) : Score {
    override fun next(winner: Player): Score {
        error("game over")
    }

    override val placard: String get() = "Game ${winner.name}"

    override val isGame: Boolean
        get() = true
}





