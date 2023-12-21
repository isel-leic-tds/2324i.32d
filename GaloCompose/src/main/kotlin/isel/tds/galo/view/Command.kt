package isel.tds.galo.view

import isel.tds.galo.model.*
import isel.tds.galo.storage.Storage

class Command(
    val argSyntax: String = "",
    val isToFinish: Boolean = false,
    val execute: Clash.(List<String>) -> Clash = { _ -> throw IllegalStateException("GameOver") }
)

fun play(): Command =
    Command(argSyntax = "position"){ args ->
        check(this is ClashRun ){"Game not started"}

        val arg = requireNotNull(args.firstOrNull()){"Missing index"}
        val idx = requireNotNull(arg.toIntOrNull()){"Invalid index $arg"}

        this.play(idx.toPosition())
    }




fun getCommands(): Map<String, Command> {
    return mapOf<String, Command>(
        "PLAY" to play(),
        "EXIT" to Command(isToFinish = true){ _ ->
            this.also{
                it.deleteIfIsOwner()
            }
        },
        "SCORE" to Command { _ ->
            this.also {
                check( it is ClashRun){"Game not started"}
                it.game.showScore()
            }
        },
        "CREATE" to Command("name"){ args ->
                val name = requireNotNull(args.firstOrNull()) { "Missing name" }
                this.startClash(name)
        },
        "JOIN" to Command("name"){args ->
                val name = requireNotNull(args.firstOrNull()) { "Missing name" }
                this.joinClash(name)
        },
//        "REFRESH" to Command{_ ->
//                check(this is ClashRun ){"Game not started"}
//                this.refreshClash()
//        },
    )
}




