package isel.tds.galo.model

enum class Player {
    X, O;
    val other get() = if( this == X) O else X
}


fun String.toPlayerOrNull() = Player.entries.firstOrNull {it.name==this}
fun String.toPlayer() = Player.valueOf(this )