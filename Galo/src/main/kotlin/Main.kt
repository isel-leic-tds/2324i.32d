import isel.tds.galo.model.*
import isel.tds.galo.view.Command
import isel.tds.galo.view.getCommands
import isel.tds.galo.view.readCommandLine
import isel.tds.galo.view.show
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

fun main() {

    var board: Board? = null

    val commands = getCommands()
    while(true){
        val (name,args)  = readCommandLine()
        val cmd = commands[name]
        if(cmd==null)println("Invalid Command $name")
        else
            try {
                board = cmd.execute(args, board)
            }catch (e: IllegalStateException){
                println(e.message)
            }
            catch (e: IllegalArgumentException){
                println(e.message)
            }
        board?.show()
    }
}







