fun main() {
//        val value1 = 1
//        val value2 = 1
//
//        println("value1 == value2="+ (value1 == value2) )
//
//        val value3 = 1
//        val value4 = 2
//
//        println("value3 == value4="+ (value3 == value4) )

    val aa1 = A(1)
    val aa2 = A(1)

    println("aa1 == aa2=" + (aa1 == aa2))
    println("aa1 === aa2=" + (aa1 === aa2))
    println(aa1)


    val dd1 = DateOptimized(2020, 9, 12)

    val dd2 = dd1

    println(dd2)

    val d1 = DateOptimizedValue(2020, 9, 12)

    val d2 = d1

    println(d2)
}

data class A(val a1: Int) {
    override fun toString(): String {
        return "Batatas:" + a1
    }
}