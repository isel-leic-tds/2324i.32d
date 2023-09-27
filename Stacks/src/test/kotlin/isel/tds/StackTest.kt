package isel.tds

import org.junit.jupiter.api.Test

class StackTest {

    @Test
    fun `Imutable stack Creation`() {
        val empty = Stack<Char>()   // Immutable empty stack
        val one = empty.push('A')
        val two = one.push('B')
        print(empty.isEmpty())   // Output: true
        print(one.top)           // Output: A
        print(two.top)           // Output: B

        var stk = Stack<Int>().push(1).push(2).push(3)
        while (!stk.isEmpty()) {
            val res = stk.pop2()
            print(res.first)
            stk = res.second
        }  // Output: 321
    }
}