package isel.tds

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MutableStackTest{

    @Test fun `equality of stacks`() {
        val sut = MutableStack<Char>()
        sut.push('A'); sut.push('B')
        val sut2 = MutableStack<Char>()
        sut2.push('A'); sut2.push('B')
        assertEquals(sut, sut2)
        sut2.pop()
        assertNotEquals(sut, sut2)
    }

}