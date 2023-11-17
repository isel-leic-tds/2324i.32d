import isel.tds.galo.model.*
import isel.tds.galo.mongo.MongoDriver
import isel.tds.galo.storage.BoardSerializer
import isel.tds.galo.storage.GameSerializer
import isel.tds.galo.storage.MongoStorage
import isel.tds.galo.storage.TextFileStorage
import isel.tds.galo.view.getCommands
import isel.tds.galo.view.readCommandLine
import isel.tds.galo.view.show
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

fun main() {
    MongoDriver("galo").use { driver ->

//    val storage = TextFileStorage<String, Game>("saves", GameSerializer)
        val storage = MongoStorage<String, Game>("games", driver, GameSerializer)
        var clash: Clash = Clash(storage)
        val commands = getCommands()

        while (true) {
            val (name, args) = readCommandLine()
            val cmd = commands[name]
            if (cmd == null) println("Invalid Command $name")
            else
                try {
                    clash = cmd.execute(args, clash)
                    if (cmd.isToFinish) break
                } catch (e: IllegalStateException) {
                    println(e.message)
                } catch (e: IllegalArgumentException) {
                    println(e.message)
                }
            clash.show()

        }
    }
}









