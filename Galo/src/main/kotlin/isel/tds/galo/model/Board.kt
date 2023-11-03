package isel.tds.galo.model

const val BOARD_SIZE = 3
const val BOARD_CELLS: Int = BOARD_SIZE * BOARD_SIZE


typealias BoardCells = Map<Position,Player>
sealed class Board(val boardCells: BoardCells){
    override fun equals(other: Any?): Boolean {
        return other is Board && boardCells == other.boardCells
    }
}
class BoardRun(boardCells: BoardCells, val turn: Player): Board(boardCells){
    override fun equals(other: Any?): Boolean {
        return super.equals(other) && other is BoardRun && turn == other.turn
    }
}
class BoardWin(boardCells: BoardCells, val winner: Player): Board(boardCells)
class BoardDraw(boardCells: BoardCells) : Board(boardCells)

fun Board(start: Player = Player.X): Board = BoardRun(emptyMap(),start)

fun Board.play(playPositionIdx: Position): Board = when(this){
    is BoardRun -> {
        require(boardCells[playPositionIdx] == null) { "Position $playPositionIdx used" }
        val boardCellsAfterPlay = boardCells + (playPositionIdx to turn)
        when {
            isWinner(playPositionIdx,boardCellsAfterPlay) -> BoardWin(boardCellsAfterPlay, turn)
            boardCellsAfterPlay.size == BOARD_CELLS -> BoardDraw(boardCellsAfterPlay)
            else -> BoardRun(boardCellsAfterPlay, turn.other)
        }
    }
    is BoardDraw, is BoardWin -> error("Game Over")
}

//class Board (
//    val boardCells: List<Player?> = List(BOARD_CELLS){null},
//    val turn: Player = Player.X,
//    val winner: Player? = null
//    )
//{
//    require( canPlay(playPositionIdx)){"Not a free position"}
//    val boardCellsAfterMove = boardCells
//        .mapIndexed {
//                    idx, c -> if (idx == playPositionIdx.index) turn else c
//        }
//    return Board(
//        boardCellsAfterMove,
//        turn = turn.other,
//        winner = winner(playPositionIdx, boardCellsAfterMove)
//    )
//}

//fun Board.winner(pos: Position, moves: List<Player?>): Player? {
//    val player = checkNotNull(moves[pos.index])
//    if (moves.count { it == player } < BOARD_SIZE) return null
//    return player.takeIf {
//        //Validate Horizontal lines/Rows
//        // -> 0, 1, 2 || 3, 4, 5 || 6, 7, 8
//        (0..<BOARD_SIZE).all {
//            moves[it + pos.row * BOARD_SIZE] == player
//        } ||
//        //Validate Vertical Columns
//        // -> 0, 3, 6 || 1, 4, 7 || 2, 5, 8
//        (0..<BOARD_CELLS step BOARD_SIZE).all {
//            moves[it + pos.col] == player
//        } ||
//        // Validate \ -> idxs= 0, 4, 8, backslash
//        pos.backSlash && (0..<BOARD_CELLS step BOARD_SIZE + 1).all {
//            moves[it] == player
//        } ||
//        // Validate / -> idxs=2, 4, 6, slash or forward slash
//        pos.slash && (BOARD_SIZE - 1..<BOARD_CELLS - 1 step BOARD_SIZE - 1).all {
//            moves[it] == player
//        }
//    }
//}

private fun isWinner(pos: Position, boardCells: BoardCells): Boolean =
    boardCells.size >= BOARD_SIZE*Player.entries.size-1 &&
            boardCells.filter { it.value == boardCells[pos] }.keys.run {
                count{ it.row == pos.row } == BOARD_SIZE ||
                count{ it.col == pos.col } == BOARD_SIZE ||
                count{ it.slash } == BOARD_SIZE ||
                count{ it.backSlash } == BOARD_SIZE
            }


//fun Board.isWinner(player: Player): Boolean =
//    // Validate \ -> idxs= 0, 4, 8
//    (0 .. 8 step 4).all { boardCells[it] == player } ||
//    //(boardCells[0]==player && boardCells[4]==player && boardCells[8]==player) ||
//    // Validate / -> idxs=2, 4, 6
//    (2 .. 6 step 2).all { boardCells[it] == player } ||
//    //Validate Horizontal lines/Rows
//    // -> 0, 1, 2 || 3, 4, 5 || 6, 7, 8
//    (0..6 step 3).any { row -> (row..row+2).all { boardCells[it] == player } } ||
//    //Validate Vertical Columns
//    // -> 0, 3, 6 || 1, 4, 7 || 2, 5, 8
//    (0..2 ).any { col -> (col..col+6 step 3).all { boardCells[it] == player } }
//
//fun Board.isDraw(): Boolean = boardCells.all { it != null } && winner == null
//
//fun Board.canPlay(positionIdx: Position): Boolean = boardCells[positionIdx.index]==null
