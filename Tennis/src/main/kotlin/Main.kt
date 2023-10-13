import isel.tds.tennis._01_singleclass.Score
import isel.tds.tennis._01_singleclass.initialScore
import isel.tds.tennis.io.readWinner



fun main(args: Array<String>) {
//    val player = readWinner()
//
//    println("winner read:"+player.name)

    var score: Score = initialScore()
    do {
        println(score.placard)
        score = score.next(readWinner())
    } while (!score.isGame)
    println("Final result")
    println(score.placard)
}

