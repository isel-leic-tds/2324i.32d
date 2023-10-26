package isel.tds.galo.view

data class CommandLine(
    val name: String,
    val args: List<String>
)

fun readCommandLine(): CommandLine {
    println(">")
    val line = readln().split(" ").filter { it.isNotBlank() }

    return if(line.isEmpty()) readCommandLine()
        else CommandLine(line.first().uppercase(),line.drop(1))
}