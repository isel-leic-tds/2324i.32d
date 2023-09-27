package isel.tds

class Stack<T> private constructor(private val head: Node<T>?) {
    private class Node<E>(val elem: E, val next: Node<E>?)

    constructor() : this(null)

    fun push(t: T): Stack<T> = Stack(Node(t, head))

    fun pop(): Stack<T> = Stack(first.next)

    fun isEmpty(): Boolean = head == null

    fun pop2(): Pair<T, Stack<T>> = top to Stack(first.next)
    

    val top: T get() = first.elem
    private val first: Node<T> get() = head ?: throw NoSuchElementException("Stack is empty.")

}