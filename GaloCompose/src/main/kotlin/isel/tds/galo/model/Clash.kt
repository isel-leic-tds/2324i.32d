package isel.tds.galo.model

import isel.tds.galo.storage.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

typealias GameStorage = Storage<String, Game>

open class Clash( val gs: GameStorage)

class ClashRun(
    gs: GameStorage,
    val id: String,
    val me: Player,
    val game: Game
): Clash(gs)


fun Clash.play(toPosition: Position): Clash {
    check(this is ClashRun) { "Clash not started" }
    check((game.board as BoardRun).turn == me) { "Not your turn" }
    val gameAfter = this.game.play(toPosition)
    gs.update(id, gameAfter)
    return ClashRun( gs, id, me, gameAfter )
}

fun Clash.startClash(name: String): Clash {
    val game = Game(firstPlayer = Player.X).newBoard()
    gs.create(name, game)
    return ClashRun( gs, name, Player.X, game)
}

fun Clash.joinClash(name: String): Clash {
    val game = gs.read(name) ?: error("Clash $name not found")
    return ClashRun( gs, name, Player.O, game)
}
//fun Clash.refreshClash(): Clash {
//    check(this is ClashRun) { "Clash not started" }
//    val game = gs.read(id) ?: error("Clash $id not found")
//    return ClashRun( gs, id, me, game)
//}
class NoChangesException : IllegalStateException("No changes")
class GameDeletedException : IllegalStateException("Game deleted")


suspend fun Clash.refreshClash(): Clash {
    check(this is ClashRun) { "Clash not started" }
    val gameAfter = gs.slowRead(id) ?: throw GameDeletedException()
//    val gameAfter = gs.read(id) ?: throw GameDeletedException()
    if (game.board == gameAfter.board) throw NoChangesException()
    return ClashRun( gs, id, me, gameAfter )
}

suspend fun GameStorage.slowRead(key: String): Game? {
    fun log(label: String) {
        println("$label: thread=${Thread.currentThread().name} " +
                "time=${System.currentTimeMillis()/1000}")
    }
    log("slowRead 1")
    val res = withContext(Dispatchers.IO){
        log("slowRead 2")
        Thread.sleep(5000)
        log("slowRead 3")
        read(key)
    }
    log("slowRead 4")
    return res
}

fun Clash.deleteIfIsOwner() {
    if (this is ClashRun && me==Player.X)
        gs.delete(id)
}

fun Clash.newBoard():Clash  {
    check(this is ClashRun) { "Clash not started" }
    val newGame = game.newBoard().also { gs.update(id,it) }
    return ClashRun(gs, id, me, newGame)
}

fun Clash.canNewBoard() = this is ClashRun && game.board is BoardWin
