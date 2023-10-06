package isel.tds

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

class StackNotEmptyTest {

    @Test
    fun `test 1`() {
        val s = stackOf<String>("AA", "BB")

        assertFalse(s.isEmpty())

        var l = stackOf(2) { idx -> "XPTO" + idx }
        println(l)
    }
}