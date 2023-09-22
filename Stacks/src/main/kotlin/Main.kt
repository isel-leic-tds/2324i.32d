import isel.tds.MutableStack

fun main(args: Array<String>) {
    println("Hello Stacks!")


    val stack1 = MutableStack<String>()

    stack1.push("ISEL")
    stack1.push("ISCAL")
    println( stack1.pop() )
    println(stack1.top)
    println( stack1.pop() )

//    println(stack1.pop())
//    println(stack1.top)


}
