package isel.tds.galo.view

import isel.tds.galo.model.*
import isel.tds.galo.storage.Storage

class Command(
    val argSyntax: String = "",
    val isToFinish: Boolean = false,
    val execute: (List<String>, Clash) -> Clash = { _, _ -> throw IllegalStateException("GameOver") }
)

fun play(): Command =
    Command(argSyntax = "position"){ args, clash ->
        check(clash is ClashRun ){"Game not started"}

        val arg = requireNotNull(args.firstOrNull()){"Missing index"}
        val idx = requireNotNull(arg.toIntOrNull()){"Invalid index $arg"}

        clash.play(idx.toPosition())
    }




fun getCommands(): Map<String, Command> {
    return mapOf<String, Command>(
        "PLAY" to play(),
        "EXIT" to Command(isToFinish = true){ _, clash ->
            clash.also{
                it.deleteIfIsOwner()
            }
        },
        "SCORE" to Command { _, clash ->
            clash.also {
                check( it is ClashRun){"Game not started"}
                it.game.showScore()
            }
        },
        "CREATE" to Command("name"){ args, clash ->
                val name = requireNotNull(args.firstOrNull()) { "Missing name" }
                clash.startClash(name)
        },
        "JOIN" to Command("name"){args, clash ->
                val name = requireNotNull(args.firstOrNull()) { "Missing name" }
                clash.joinClash(name)
        },
        "REFRESH" to Command{_, clash ->
                check(clash is ClashRun ){"Game not started"}
                clash.refreshClash()
        },
    )
}




