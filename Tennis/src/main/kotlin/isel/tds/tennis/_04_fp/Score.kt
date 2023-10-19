package isel.tds.tennis._04_fp

import isel.tds.tennis.Player
import isel.tds.tennis._04_fp.Points.*

enum class Points(val value: Int) {
    LOVE(0), FIFTEEN(15), THIRTY(30), FORTY(40), ADVANTAGE(50), GAME(60);

    fun next(): Points = values()[ordinal + 1]

}

class Score(
    val placard: String,
    val isGame: Boolean = false,  // default parameter
    val next: (win: Player) -> Score
)

private fun game(winner: Player) = Score(
    placard = "Game $winner",
    isGame = true,
    next = { win: Player -> error("Is over") }
)

private fun advantage(player: Player): Score = Score(
    placard = "Advantage $player",
    isGame = false,
    next = { win -> if (win == player) game(win) else deuce() }
)

private fun deuce() = Score("Deuce", next = ::advantage)

private fun byPoints(pointsA: Points, pointsB: Points): Score = Score(
    placard = "${pointsA.value} - ${pointsB.value}",
    next = {

        when {
            (it == Player.A && pointsA == FORTY)
                    || (it == Player.B && pointsB == FORTY) -> game(it)

            (it == Player.B && pointsA == FORTY && pointsB == THIRTY)
                    || (it == Player.A && pointsB == FORTY && pointsA == THIRTY) -> deuce()

            it == Player.A -> byPoints(pointsA.next(), pointsB)
            else -> byPoints(pointsA, pointsB.next())
        }
    }
)

fun initialScore(): Score = byPoints(LOVE, LOVE)







