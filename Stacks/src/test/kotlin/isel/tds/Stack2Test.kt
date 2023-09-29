package isel.tds

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class Stack2Test {

    @Test
    fun `Imutable stack Creation`() {
        val empty = Stack2<Char>()   // Immutable empty stack
        val one = empty.push('A')
        val two = one.push('B')
        print(empty.isEmpty())   // Output: true
        print(one.top)           // Output: A
        print(two.top)           // Output: B

        var stk = Stack2<Int>().push(1).push(2).push(3)
        while (!stk.isEmpty()) {
            val res = stk.pop2()
            print(res.first)
            stk = res.second
        }  // Output: 321
    }

    @Test
    fun `empty stack conditions`() {
        val stk = Stack2<Int>()
        assertTrue(stk.isEmpty())
        assertFailsWith<NoSuchElementException> { stk.top }
        assertFailsWith<NoSuchElementException> { stk.pop() }
    }

    @Test
    fun `not empty stack conditions`() {
        val stk = Stack2<String>().push("ISEL")
        assertFalse(stk.isEmpty())
        assertEquals("ISEL", stk.top)
        assertTrue(stk.pop().isEmpty())
    }

    @Test
    fun `stack operations`() {
        val stk = Stack2<Char>().push('A').push('B').push('C')
        assertEquals('C', stk.top)
        assertEquals('B', stk.pop().top)
        val one = stk.pop().pop()
        assertEquals('A', one.top)
    }

    @Test
    fun `Iteration v1 test`() {
        val stk = Stack2<Int>().push(1).push(2).push(3)
//        val aa = emptyList<Int>()
        var sum = 0
        for (e in stk) sum += e
        println("sum = $sum, top = ${stk.top}") // Output: sum = 6, top = 3


    }

    @Test
    fun `equality of stacks`() {
        var sut = Stack2<Char>()
        sut = sut.push('A').push('B')
        var sut2 = Stack2<Char>()
        sut2 = sut2.push('A').push('B')
        assertEquals(sut, sut2)
        sut2 = sut2.pop()
        assertNotEquals(sut, sut2)
    }

}