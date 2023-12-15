package isel.tds.galo.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import isel.tds.galo.model.*
import isel.tds.galo.mongo.MongoDriver
import isel.tds.galo.storage.GameSerializer
import isel.tds.galo.storage.MongoStorage

class AppViewModel(driver: MongoDriver) {

    private val storage = MongoStorage<String, Game>("games", driver, GameSerializer)
    private var clash by mutableStateOf( Clash(storage))

//    var game by mutableStateOf(Game())
    var viewScore by mutableStateOf(false)
        private set
    var inputName by mutableStateOf<InputName?>(null)
        private set
    var errorMessage by mutableStateOf<String?>(null) //ErrorDialog state
        private set

    val board: Board? get() = (clash as? ClashRun)?.game?.board
    val score: Score get() = (clash as ClashRun).game.score

    val me: Player? get() = (clash as? ClashRun)?.me

    val hasClash: Boolean get() = clash is ClashRun
    val newAvailable: Boolean get() = clash.canNewBoard()

    fun newBoard(){ clash = clash.newBoard() }

    fun showScore(){ viewScore = true}
    fun hideScore(){ viewScore = false}

    fun hideError() { errorMessage = null }

    fun play(pos: Position){
        try {
            clash = clash.play(pos)
        } catch (e: Exception) {
            errorMessage = e.message
        }
    }

    enum class InputName(val txt: String)
    { NEW("Start"), JOIN("Join") }

    fun cancelInput() { inputName = null }
    fun newGame(gameName: String) {
        clash = clash.startClash(gameName)
        inputName = null
    }

    fun joinGame(gameName: String) {
        clash = clash.joinClash(gameName)
        inputName = null
    }

    fun refreshGame() {
        try {
            clash = clash.refreshClash()
        } catch (e: Exception) {
            errorMessage = e.message
        }
    }

    fun showNewGameDialog() { inputName = InputName.NEW }
    fun showJoinGameDialog() { inputName = InputName.JOIN }

    fun exit() { clash.deleteIfIsOwner() }

}