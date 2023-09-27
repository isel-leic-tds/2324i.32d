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

    override fun equals(other: Any?): Boolean
    {
        if (other !is MutableStack<*>) return false
        var n1 = this.head
        var n2 = other.head
        while (n1 != null && n2 != null) {
            if (n1.elem != n2.elem) return false
            n1 = n1.next
            n2 = n2.next
        }
        return n1 == null && n2 == null
    }

    override fun hashCode(): Int {
        var n = head
        var hash = 0
        while (n != null) {
            hash = 31 * hash + n.elem.hashCode()
            n = n.next
        }
        return hash
    }


}
