package isel.tds.galo.model

import isel.tds.galo.storage.Storage

typealias GameStorage = Storage<String, Game>

open class Clash( val gs: GameStorage)

class ClashRun(
    gs: GameStorage,
    val id: String,
    val me: Player,
    val game: Game
): Clash(gs)


fun ClashRun.play(toPosition: Position): Clash {
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
fun ClashRun.refreshClash(): Clash {
    val game = gs.read(id) ?: error("Clash $id not found")
    return ClashRun( gs, id, me, game)
}

fun Clash.deleteIfIsOwner() {
    if (this is ClashRun && me==Player.X)
        gs.delete(id)
}


