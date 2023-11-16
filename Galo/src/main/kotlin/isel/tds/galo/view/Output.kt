package isel.tds.galo.view

import isel.tds.galo.model.*

private val separator = "---+".repeat(BOARD_SIZE-1)+"---"

fun Board.show() {
//    println(boardCells[0] + " | " + boardCells[1] + " | " +  boardCells[2] )
//    println("---+---+---")
//    println(boardCells[3] + " | " + boardCells[4] + " | " +  boardCells[5] )
//    println("---+---+---")
//    println(boardCells[6] + " | " + boardCells[7] + " | " +  boardCells[8] )

//    for( idx in 0..6 step 3){
//        println(" ${boardCells.subList(idx, idx+3)
//            .map( {if(it == null) " " else it.name}).joinToString ( " | " )} ")
//        if( idx<6) println("---+---+---")
//    }

    Position.values.forEach { pos ->
        print(" ${boardCells[pos] ?: ' '} ")
        if (pos.col == BOARD_SIZE - 1) {
            println()
            if (pos.row < BOARD_SIZE-1) println(separator)
        } else print("|")
    }
    println(when(this){
        is BoardWin -> "Winner: ${winner.name}"
        is BoardRun -> "Turn: $turn"
        is BoardDraw -> "Draw"
    })
}


fun Game.show() = board?.show()

fun Game.showScore() {
    print("Score:")
    score.forEach { (player, value) ->
        print(" ${player ?: "Draws"}=$value ")
    }
    println()
}

private fun Clash.show() {
    if (this is ClashRun) {
        println("Clash: $id Player: $sidePlayer")
        game.board?.show()
    }
    else println("Clash not started")
}