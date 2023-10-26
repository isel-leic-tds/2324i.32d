package isel.tds.galo.view

import isel.tds.galo.model.Board
import isel.tds.galo.model.play

abstract class Command( val argSyntax: String = "") {
    open fun execute(args: List<String>, board: Board? ): Board = throw IllegalStateException("GameOver")

    open val isToFinish = false
}

object Play : Command("idx" ){
    override fun execute(args: List<String>, board: Board?): Board {
        checkNotNull(board){"Game not started"}

        val arg = requireNotNull(args.firstOrNull()){"Missing index"}
        val positionIdx = requireNotNull(arg.toIntOrNull()){"Invalid index $arg"}
        return board.play(positionIdx)
    }

}

fun getCommands(): Map<String, Command> {
    return mapOf<String, Command>(
        "PLAY" to Play,
        "NEW" to object : Command(){
            override fun execute(args: List<String>, board: Board?): Board = Board()
        },
        "EXIT" to object: Command(){
            override val isToFinish = true
        }
    )
}