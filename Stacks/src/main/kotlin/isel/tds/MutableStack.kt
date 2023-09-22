package isel.tds

class MutableStack1<T> {

    private var elems = emptyList<T>()
    fun push(elem: T) {
        elems = elems + elem
    }
    fun pop(): T {
        val myTop = top
        elems = elems.dropLast(1)
        return myTop
    }
    val top: T get() = elems.last()
}

class MutableStack2<T> {

    private val elems = mutableListOf<T>()
    fun push(elem: T){
       elems.add(elem)
    }
    val top: T get() =  elems.last()
    fun pop(): T = top.also {elems.removeLast()}


}

class MutableStack<T> {

    private class Node<T>(val elem: T, val next: Node<T>?)
    private var head: Node<T>? = null
    fun push(elem: T){
        head = Node(elem, head)
    }
    val top: T get() = head?.elem ?: throw NoSuchElementException("List is empty.")
    fun pop(): T {
        val myTop = top
        head = head?.next
        return myTop
    }


}
