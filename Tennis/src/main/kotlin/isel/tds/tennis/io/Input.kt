package isel.tds.tennis.io

import isel.tds.tennis.Player


fun readWinner(): Player {
    return Player.valueOf(readWinnerCharRecursive().toString())
}

fun readWinnerChar(): Char {
    var winner: Char?
    do {
        println("Choose winner ('A' or 'B')")
        winner = readln()?.firstOrNull()?.uppercaseChar()
    } while (winner == null || winner !in "AB")
    return winner
}

fun readWinnerCharRecursive(): Char {
    println("Choose winner ('A' or 'B')")
    return readln()?.firstOrNull()?.uppercaseChar()?.takeIf { it in "AB" } ?: readWinnerCharRecursive()

}
