package isel.tds.galo.storage

import isel.tds.galo.model.*
import kotlin.collections.emptyMap

object BoardSerializer: Serializer<Board> {
    override fun serialize(data: Board): String =
        when (data) {
            is BoardRun -> "run ${data.turn}"
            is BoardWin -> "win ${data.winner}"
            is BoardDraw -> "draw"
        } + " | " +
                data.boardCells.entries.joinToString(" ")
                { (pos, plyr) -> "${pos.index}:$plyr" }


    override fun deserialize(text: String): Board {

        val splittedText = text.split(" | ")

        val left = splittedText[0]
        val right = splittedText[1]
        val moves =
                if (right.isBlank()) emptyMap<Position,Player>()
                else right.split(" ")
                        .map { it.split(":") }
                        .associate { (idx, plyr) ->
                            Position(idx.toInt()) to plyr.toPlayer()
                        }
            val (type, plyr) = left.split(" ")
            return when (type) {
                "run" -> BoardRun(moves, plyr.toPlayer())
                "win" -> BoardWin(moves, plyr.toPlayer())
                "draw" -> BoardDraw(moves)
                else -> error("Invalid board type: $type")
            }
    }
}