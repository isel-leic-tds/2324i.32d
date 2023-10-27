package isel.tds.galo.model

@JvmInline
value class Position private constructor(val index: Int) {

    val row: Int get() = index / BOARD_SIZE

    val col: Int get() = index % BOARD_SIZE

    val backSlash: Boolean get() = row == col

    val slash: Boolean get() = row + col == BOARD_SIZE -1

    companion object{
        val values = List(BOARD_CELLS){ idx -> Position(idx) }

        operator fun invoke( index: Int): Position{
            require( index in 0..< BOARD_CELLS){"out of bounds"}
            return values[index]
        }

    }
}
fun Int.toPosition(): Position = Position(this)
