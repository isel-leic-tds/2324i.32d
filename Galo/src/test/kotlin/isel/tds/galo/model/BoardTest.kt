package isel.tds.galo.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class BoardTest{
    @Test
    fun `test empty board`(){
        val sut = Board()
        assertFalse(sut.isWinner('X'))
        assertFalse(sut.isWinner('O'))
        assertFalse(sut.isDraw())
    }
    @Test
    fun `test draw board`(){
        val sut = Board( listOf(
            'O', 'X','O',
            'X', 'X','O',
            'O', 'O','X'
        )
        )
        assertFalse(sut.isWinner('X'))
        assertFalse(sut.isWinner('O'))
        assertTrue(sut.isDraw())
    }

    @Test
    fun `test diagonal backslash winner board`(){
        var sut = Board( listOf(
            'X', 'X','O',
            'X', 'X','O',
            'O', 'O','X'
        )
        )
        assertTrue(sut.isWinner('X'))
        assertFalse(sut.isWinner('O'))
        assertFalse(sut.isDraw())

        sut = Board( listOf(
            'O', 'X','X',
            'X', 'O','O',
            'X', 'X','O'
        )
        )
        assertFalse(sut.isWinner('X'))
        assertTrue(sut.isWinner('O'))
        assertFalse(sut.isDraw())
    }

    @Test
    fun `Test Row 2 X win`() {
        val sut = Board(
            listOf(
                'X', 'X', ' ',
                'X', 'O', 'X',
                'O', 'O', 'O'
            )
        )

        assertFalse(sut.isWinner('X'))
        assertTrue(sut.isWinner('O'))
        assertFalse(sut.isDraw())
    }
    //TODO: add more tests
}
