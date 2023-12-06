package isel.tds.galo.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import isel.tds.galo.model.*

class AppViewModel {

    var game by mutableStateOf(Game())
    var viewScore by mutableStateOf(false)
        private set

    fun newBoard(){ game = game.newBoard() }

    fun showScore(){ viewScore = true}
    fun hideScore(){ viewScore = false}

    fun play(pos: Position){
        if(game.board is BoardRun)
            game = game.play(pos)
    }

}