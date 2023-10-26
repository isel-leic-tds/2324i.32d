package isel.tds.galo.view

import isel.tds.galo.model.Board
import isel.tds.galo.model.isDraw
import isel.tds.galo.model.isWinner

fun Board.show() {
//    println(boardCells[0] + " | " + boardCells[1] + " | " +  boardCells[2] )
//    println("---+---+---")
//    println(boardCells[3] + " | " + boardCells[4] + " | " +  boardCells[5] )
//    println("---+---+---")
//    println(boardCells[6] + " | " + boardCells[7] + " | " +  boardCells[8] )

    for( idx in 0..6 step 3){
        println(" ${boardCells.subList(idx, idx+3).joinToString ( " | " )} ")
        if( idx<6) println("---+---+---")
    }
    when{
        isWinner('X') -> println("Winner: X")
        isWinner('O') -> println("Winner: O")
        isDraw() -> println("Draw")
        else -> println("Turn: $turn")
    }
}