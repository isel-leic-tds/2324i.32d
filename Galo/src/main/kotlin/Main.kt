import isel.tds.galo.model.*

fun main() {

    var board: Board? = null
    while(board==null || (!board.isWinner('X') && !board.isWinner('O') )){
        println(">")
        val cmd = readln().uppercase().split(" ")

        when (cmd[0]) {
            "NEW" -> board = Board()
            "EXIT" -> return
            "PLAY" -> {
                board?.let {
                    val positionIdx = cmd[1].toInt()
                    if (it.canPlay(positionIdx))
                        board = board?.play(positionIdx)
                    else
                        println("Granda burro!!!!\njoga outra vez...")
                }
            }
            else -> println("Invalid command ${cmd}")
        }
        board?.show()
    }
}



