package isel.tds.galo.model

class Board (
    val boardCells: List<Char> = listOf(
        ' ', ' ',' ',
        ' ', ' ',' ',
        ' ', ' ',' '
    ),
    val turn: Char = 'X')

fun Board.play(playPositionIdx: Int): Board {
    require( playPositionIdx>=0 && playPositionIdx < 9){"out of bounds"}
    require( canPlay(playPositionIdx)){"Not a free position"}

    return Board(
        boardCells.mapIndexed { idx, c -> if (idx == playPositionIdx) turn else c },
        turn = if (turn == 'X') 'O' else 'X'
    )
}

fun Board.isWinner(player: Char): Boolean =
    // Validate \ -> idxs= 0, 4, 8
    (0 .. 8 step 4).all { boardCells[it] == player } ||
    //(boardCells[0]==player && boardCells[4]==player && boardCells[8]==player) ||
    // Validate / -> idxs=2, 4, 6
    (2 .. 6 step 2).all { boardCells[it] == player } ||
    //Validate Horizontal lines/Rows
    // -> 0, 1, 2 || 3, 4, 5 || 6, 7, 8
    (0..6 step 3).any { row -> (row..row+2).all { boardCells[it] == player } } ||
    //Validate Vertical Columns
    // -> 0, 3, 6 || 1, 4, 7 || 2, 5, 8
    (0..2 ).any { col -> (col..col+6 step 3).all { boardCells[it] == player } }

fun Board.isDraw(): Boolean = boardCells.all { it != ' ' } && !isWinner('X') && !isWinner('O')


fun Board.canPlay(positionIdx: Int): Boolean = boardCells[positionIdx]==' '
