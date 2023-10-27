package isel.tds.galo.model

import isel.tds.galo.model.Player.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class BoardTest{
    @Test
    fun `test empty board`(){
        val sut = Board()
//        assertFalse(sut.winner == X)
//        assertFalse(sut.winner == O)
//        assertFalse(sut.isDraw())

        assertTrue( sut is BoardRun)

    }
    @Test
    fun `test draw board`(){
        val sut = BoardRun( mapOf(
            Position(0) to O, Position(1) to X, Position(2) to O,
            Position(3) to X, Position(4) to X, Position(5) to O,
            Position(6) to O, Position(7) to O
        ),
            turn = X
        ).play(Position(8))

//        assertFalse(sut.winner == X)
//        assertFalse(sut.winner == O)
//        assertTrue(sut.isDraw())
        assertTrue( sut is BoardDraw)
    }

    @Test
    fun `test diagonal backslash winner board`(){
        var sut = BoardRun( mapOf(
            Position(0) to X, Position(1) to X, Position(2) to O,
            Position(3) to X, Position(4) to X, Position(5) to O,
            Position(6) to O, Position(7) to O
        ),turn = X
        ).play( 8.toPosition())

        assertTrue( sut is BoardWin)
        assertTrue( sut is BoardWin && sut.winner == X)
        assertFalse(sut is BoardWin && sut.winner == O)

        sut = BoardRun( mapOf(
            Position(0) to O, Position(1) to X, Position(2) to X,
            Position(3) to X, Position(4) to O, Position(5) to O,
            Position(6) to X, Position(7) to X
        ),
            turn = O
        ).play( 8.toPosition())

        assertTrue(sut is BoardWin)
        assertFalse(sut is BoardWin && sut.winner == X)
        assertTrue(sut is BoardWin && sut.winner == O)
//        assertFalse(sut.isDraw())
    }

    @Test
    fun `Test Row 2 X win`() {
        val sut = BoardRun(
            mapOf(
                Position(0) to X, Position(1) to X,
                Position(3) to X, Position(4) to O, Position(5) to X,
                Position(6) to O, Position(7) to O
            ),
            turn = O
        ).play( 8.toPosition())

        assertTrue(sut is BoardWin)
        assertFalse(sut is BoardWin && sut.winner == X)
        assertTrue(sut is BoardWin && sut.winner == O)
//        assertFalse(sut.isDraw())
    }
    //TODO: add more tests
}
