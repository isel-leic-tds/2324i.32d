import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import isel.tds.galo.model.*


val CELL_SIDE = 100.dp
val GRID_THICKNESS = 5.dp
val BOARD_SIDE = CELL_SIDE * BOARD_SIZE + GRID_THICKNESS* (BOARD_SIZE-1)

@Composable
@Preview
fun FrameWindowScope.App(exitFunction: () -> Unit) {
//    var player by remember { mutableStateOf(Player.X) }
    var board by remember { mutableStateOf(Board()) }
    MenuBar  {
        Menu("Game") {
            Item("New Board", onClick = { board = Board()})
            Item("Exit", onClick = exitFunction)
        }
    }
    MaterialTheme {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            BoardView(
                boardCells = board.boardCells,
                onClick = {pos ->
                    if(board is BoardRun)
                        board = board.play(pos)
                })
            StatusBar(board)
        }
    }
}
@Composable
private fun FrameWindowScope.MenuActions(exitFunction: () -> Unit) {
    MenuBar  {
        Menu("Game") {
            Item("New Board", onClick = { println("new board") })
            Item("Exit", onClick = exitFunction)
        }
    }
}


@Composable
fun StatusBar(board: Board) =
    Row {
        val (txt, player) = when(board){
            is BoardRun -> "Turn:" to board.turn
            is BoardWin -> "Winner:" to board.winner
            is BoardDraw -> "Draw" to null
        }
        Text(text=txt, style=MaterialTheme.typography.h4 )
        Cell(player, size = 50.dp)
    }
@Composable
fun BoardView(boardCells: BoardCells, onClick: (Position)->Unit) =
    Column(
        modifier = Modifier
            .background(Color.Black)
            .size(BOARD_SIDE),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(BOARD_SIZE){ row ->
            Row(
                modifier = Modifier.fillMaxWidth().height(CELL_SIDE),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                repeat(BOARD_SIZE){col ->
                    val pos = Position(row, col)
                    Cell(
                        boardCells[pos],
                        onClick = { onClick(pos)} )
                }
            }
        }
    }


@Composable
fun Cell(player: Player?, size: Dp = 100.dp, onClick: ()->Unit={}){

    val modifier = Modifier.size(size)
        .background(color = Color.White)
    if( player == null) {
        Box(modifier.clickable(onClick = onClick))
    }else {
        val filename = when (player) {
            Player.X -> "cross.png"
            Player.O -> "circle.png"
        }
        Image(
            painter = painterResource(filename),
            contentDescription = "Player $player",
            modifier = modifier
        )
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Mega jogo galo",
        state = WindowState(size= DpSize.Unspecified)
    ) {
//        MenuActions(::exitApplication)
        App(::exitApplication)
    }
}



//@Composable
//@Preview
//fun testPlayerView(){
//    PlayerView(Player.X)
//}