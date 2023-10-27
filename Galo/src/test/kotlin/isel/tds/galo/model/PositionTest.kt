package isel.tds.galo.model

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.test.assertFailsWith

class PositionTest {

    @Test
    fun testInitiation() {

        val p01 = Position(0)
        assertEquals(0, p01.index)

        val p02 = Position(0)


        assertTrue(p01 == p02)
    }

    @Test
    fun testInitiationWrongValues() {
        assertFailsWith<IllegalArgumentException> { Position(15) }
    }
}