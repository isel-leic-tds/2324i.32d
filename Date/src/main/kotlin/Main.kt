
    fun main(){
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

        println("aa1 == aa2="+ (aa1 == aa2))
        println("aa1 === aa2="+ (aa1 === aa2) )
        println(aa1)
    }

     data class A(val a1: Int){
         override fun toString(): String {
             return "Batatas:"+a1
         }
     }