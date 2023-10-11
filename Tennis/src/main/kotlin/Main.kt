import isel.tds.tennis.Score
import isel.tds.tennis.io.readWinner

fun initialScore() = Score(0, 0)

fun main(args: Array<String>) {
    val player = readWinner()

    println("winner read:"+player.name)

//    var score: Score = initialScore()
//    while(true){
//        println( score.placard)
//        score = score.next( readWinner())
//    }
}

