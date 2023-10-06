package isel.tds

interface Stack<T> : Iterable<T> {
    val top: T
    fun push(elem: T): Stack<T>
    fun pop(): Stack<T>
    fun isEmpty(): Boolean
}

fun <T> stack(): Stack<T> = StackEmpty as Stack<T>

private class Node<T>(val elem: T, val next: Node<T>?)

private object StackEmpty : Stack<Any> {
    private fun throwEmpty(): Nothing = throw NoSuchElementException("stack empty")

    override val top: Nothing get() = throwEmpty()

    override fun pop() = throwEmpty()

    override fun isEmpty(): Boolean = true

    override fun push(elem: Any) = StackNotEmpty(Node(elem, null))

    override fun iterator() = object : Iterator<Nothing> {
        override fun hasNext() = false
        override fun next() = throwEmpty()
    }
}

private class StackNotEmpty<T>(private val head: Node<T>) : Stack<T> {
    override val top: T get() = head.elem
    override fun pop(): Stack<T> = head.next?.let { StackNotEmpty(it) } ?: stack()
    override fun isEmpty(): Boolean = false
    override fun push(elem: T): Stack<T> = StackNotEmpty(Node(elem, head))
    override fun iterator(): Iterator<T> = object : Iterator<T> {
        var node: Node<T>? = head
        override fun hasNext() = node != null
        override fun next() =
            (node ?: throw NoSuchElementException("no more elements"))
                .also { node = it.next }.elem
    }
}

//fun <T> stackOf(vararg elems: T): Stack<T> {
//    var stk = stack<T>()
//    for (e in elems) stk = stk.push(e)
//    return stk
//}
//fun <T> stackOf(vararg elems: T): Stack<T> {
//    return elems.fold(stack()) { s, elem -> s.push(elem) }
//}
//fun <T> stackOf(vararg elems: T): Stack<T> {
//    if (elems.isEmpty()) return stack()
//    var n: Node<T>? = null
//    for (e in elems) n = Node(e, n)
//    return StackNotEmpty(n as Node<T>)
//}

fun <T> stackOf(vararg elems: T): Stack<T> {
    if (elems.isEmpty()) return stack()
    return StackNotEmpty(
        elems.fold(null as Node<T>?)
        { lastNode, elem -> Node(elem, lastNode) } as Node<T> as Node<T>)
}

fun <T> stackOf(size: Int, init: (index: Int) -> T): Stack<T> {
    var stk = stack<T>()
    for (i in 0..<size) stk = stk.push(init(i))
    return stk
}