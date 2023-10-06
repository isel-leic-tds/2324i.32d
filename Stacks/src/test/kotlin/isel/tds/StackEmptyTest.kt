package isel.tds

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class StackEmptyTest {

    @Test
    fun `test 1`() {
        val empty1 = stack<Int>()
        val empty2 = stack<Int>()

        assertTrue(empty1 === empty2)

        val one1 = empty1.push(1)
        val one2 = empty2.push(1)
        val one11 = empty1.push(1)
        assertFalse(one1 === one2)
        assertFalse(one1 === one11)


        var a = listOf(1, 2, 3, 4, 5)
    }
}