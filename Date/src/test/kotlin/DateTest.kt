import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test
import kotlin.test.assertFailsWith

class DateTest{

    @Test fun testDateCreation(){
        val sut = Date(2023, 9,15)
        assertNotNull(sut)
        assertEquals(2023, sut.year)
        assertEquals(9, sut.month)
        assertEquals(15, sut.day)
    }

    @Test fun testDateCreationWithDefaultDay(){
        val sut = Date(2023, 9)
        assertNotNull(sut)
        assertEquals(2023, sut.year)
        assertEquals(9, sut.month)
        assertEquals(1, sut.day)
    }
    @Test fun testDateCreationWithDefaultDayAndMonth(){
        val sut = Date(2023)
        assertNotNull(sut)
        assertEquals(2023, sut.year)
        assertEquals(1, sut.month)
        assertEquals(1, sut.day)
    }
    @Test fun testDateCreationWithDefaultMonthAndNamedParameter(){
        val sut = Date(2023, day = 3)
        assertNotNull(sut)
        assertEquals(2023, sut.year)
        assertEquals(1, sut.month)
        assertEquals(3, sut.day)
    }

    @Test fun `test Leap Year`(){
        val sut = Date(2020, 9,15)
        assertTrue(sut.leapYear)
    }

    @Test fun `test non Leap Year`(){
        val sut = Date(2023, 9,15)
        assertFalse(sut.leapYear)
    }

    @Test fun `test invalid Year`(){
        assertFailsWith<IllegalArgumentException>(){ Date(1000, 9,15)}
    }

    @Test fun `test invalid Month`(){
        assertFailsWith<IllegalArgumentException>(){ Date(2020, 15,15)}
    }

    @Test fun `test invalid Day`(){
        assertFailsWith<IllegalArgumentException>(){ Date(2020, 1, 32)}
    }

    @Test fun `test last Day Of Month`(){
        val sut = Date(2023, 9,15)
        assertEquals(30, sut.lastDayOfMonth)
    }

    @Test fun `test last Day Of Month of Feb on non leapYear`(){
        val sut = Date(2023, 2,15)
        assertEquals(28, sut.lastDayOfMonth)
    }

    @Test fun `test last Day Of Month of Feb on leapYear`(){
        val sut = Date(2020, 2,15)
        assertEquals(29, sut.lastDayOfMonth)
    }
}