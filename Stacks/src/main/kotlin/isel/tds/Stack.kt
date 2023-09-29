package isel.tds

interface Stack<T> : Iterable<T> {
    val top: T
    fun push(elem: T): Stack<T>
    fun pop(): Stack<T>
    fun isEmpty(): Boolean
}

fun <T> Stack(): Stack<T> = StackEmpty<T>()

private class Node<T>(val elem: T, val next: Node<T>?)

private class StackEmpty<T> : Stack<T> {
    private fun throwEmpty(): Nothing = throw NoSuchElementException("stack empty")

    override val top: T get() = throwEmpty()

    override fun pop(): Stack<T> = throwEmpty()

    override fun isEmpty(): Boolean = true

    override fun push(elem: T): Stack<T> = StackNotEmpty(Node(elem, null))

    override fun iterator(): Iterator<T> = object : Iterator<Nothing> {
        override fun hasNext() = false
        override fun next() = throwEmpty()
    }
}

private class StackNotEmpty<T>(private val head: Node<T>) : Stack<T> {
    override val top: T get() = head.elem
    override fun pop(): Stack<T> = head.next?.let { StackNotEmpty(it) } ?: Stack()
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

