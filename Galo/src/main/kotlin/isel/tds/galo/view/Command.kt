package isel.tds.galo.view

import isel.tds.galo.model.*
import isel.tds.galo.storage.TextFileStorage

abstract class Command( val argSyntax: String = "") {
    open fun execute(args: List<String>, game: Game ): Game = throw IllegalStateException("GameOver")

    open val isToFinish = false
}

object Play : Command("idx" ){
    override fun execute(args: List<String>, game: Game): Game {
        checkNotNull(game.board){"Game not started"}

        val arg = requireNotNull(args.firstOrNull()){"Missing index"}
        val idx = requireNotNull(arg.toIntOrNull()){"Invalid index $arg"}

        return game.play(idx.toPosition())
    }

}

fun getCommands(storage: TextFileStorage<String, Game>): Map<String, Command> {
    return mapOf<String, Command>(
        "PLAY" to Play,
        "NEW" to object : Command(){
            override fun execute(args: List<String>, game: Game): Game = game.newBoard()
        },
        "EXIT" to object: Command(){
            override val isToFinish = true
        },
        "SCORE" to object: Command() {
            override fun execute(args: List<String>, game: Game): Game =
                game.also { it.showScore() }
        },
        "SAVE" to object: Command(){
            override fun execute(args: List<String>, game: Game): Game{
                require(args.isNotEmpty()) { "Missing name" }
                requireNotNull(game.board){ "Game not started" }
                val name = args[0]
                require(name.isNotEmpty()) { "Name must not be empty" }
                storage.create(name, game)
                return game
            }
        },
        "LOAD" to object: Command() {
            override fun execute(args: List<String>, game: Game): Game {
                val name = requireNotNull(args.firstOrNull()) { "Missing name" }
                return checkNotNull(storage.read(name)) { "Game $name not found" }
            }
        }
                )
}