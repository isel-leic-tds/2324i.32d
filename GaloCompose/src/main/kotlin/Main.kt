import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import isel.tds.galo.model.*
import isel.tds.galo.mongo.MongoDriver
import isel.tds.galo.viewmodel.AppViewModel
import isel.tds.galo.viewmodel.AppViewModel.InputName


val CELL_SIDE = 100.dp
val GRID_THICKNESS = 5.dp
val BOARD_SIDE = CELL_SIDE * BOARD_SIZE + GRID_THICKNESS* (BOARD_SIZE-1)


@Composable
@Preview
fun FrameWindowScope.App(driver: MongoDriver, exitFunction: () -> Unit) {
    val scope = rememberCoroutineScope()
    val vm = remember { AppViewModel(driver, scope) }

    MenuBar  {
        Menu("Game") {
            Item("New Game", onClick = vm::showNewGameDialog)
            Item("Join Game", onClick = vm::showJoinGameDialog)
//            Item("Refresh", enabled = vm.hasClash, onClick = vm::refreshGame)//
            Item("New Board", enabled= vm.newAvailable, onClick = vm::newBoard)
            Item("Show Score", enabled = vm.hasClash, onClick = vm::showScore)
            Item("Exit", onClick = { vm.exit(); exitFunction() })
        }
    }
    MaterialTheme {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            BoardView(
                boardCells = vm.board?.boardCells,
                onClick = vm::play)
            StatusBar(vm.board, vm.me)
        }
        if(vm.viewScore){ ScoreDialog(vm.score, vm::hideScore)}
        vm.inputName?.let{
            StartOrJoinDialog(
                type = it,
                onCancel = vm::cancelInput,
                onAction = if(it==InputName.NEW) vm::newGame else vm::joinGame
            )
        }
        vm.errorMessage?.let { ErrorDialog(it, onClose = vm::hideError) }
        if(vm.isWaiting) waitingIndicator()
    }
}

@Composable
fun waitingIndicator() = CircularProgressIndicator(
    Modifier.fillMaxSize().padding(30.dp),
    strokeWidth = 15.dp
)


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DialogBase(
    title: String,
    onClose: ()->Unit,
    content: @Composable ()->Unit
) = AlertDialog(
    onDismissRequest = onClose,
    title = { Text(title, style = MaterialTheme.typography.h4) },
    text = content,
    confirmButton = { TextButton(onClick = onClose) { Text("Close") } }
)


@Composable
fun ErrorDialog(message: String, onClose: ()->Unit) =
    DialogBase("Error", onClose) {
        Text(message, style = MaterialTheme.typography.h6)
    }
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StartOrJoinDialog(
    type: InputName,
    onCancel: ()->Unit,
    onAction: (String)->Unit) {

    var name by remember { mutableStateOf("") }  // Name in edition
    AlertDialog(
            onDismissRequest = onCancel,
            title = { Text(text = "Name to ${type.txt}",
                    style = MaterialTheme.typography.h5
                        )
                    },
            text = {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name of game") }
                            )
                   },
            confirmButton = {
                    TextButton(enabled = true,//Name.isValid(name),
                            onClick = { onAction(name)}//Name(name)) }
                        ) { Text(type.txt) }
                },
            dismissButton = {
                    TextButton(onClick = onCancel){ Text("cancel") }
                }
    )
}
    

@OptIn(ExperimentalMaterialApi::class, ExperimentalStdlibApi::class)
@Composable
fun ScoreDialog(score: Map<Player?, Int>, closeDialog: () -> Unit) =
    AlertDialog(
        onDismissRequest = closeDialog,
        confirmButton = { TextButton(onClick=closeDialog){ Text("Close") } },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column( horizontalAlignment = Alignment.CenterHorizontally){
                    Player.entries.forEach { player ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Cell( player, size = 20.dp)
                            Text(
                                text = " - ${score[player]}",
                                style = MaterialTheme.typography.h4
                            )
                        }
                    }
                    Text("Draws - ${score[null]}", style = MaterialTheme.typography.h4)
                }
            }
        }
    )


@Composable
fun StatusBar(board: Board?, me: Player?) =
    Row {
        me?.let{
            Text("You ", style = MaterialTheme.typography.h4)
            Cell(player = it, size=50.dp)
            Spacer(Modifier.width(30.dp))
        }
        val (txt, player) = when(board){
            is BoardRun -> "Turn:" to board.turn
            is BoardWin -> "Winner:" to board.winner
            is BoardDraw -> "Draw" to null
            null -> "Game not started" to null
        }
        Text(text=txt, style=MaterialTheme.typography.h4 )
        Cell(player, size = 50.dp)
    }
@Composable
fun BoardView(boardCells: BoardCells?, onClick: (Position)->Unit) =
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
                        boardCells?.get(pos),
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

fun main() =
    MongoDriver("galo").use { driver ->
        application {
            Window(
                onCloseRequest = ::exitApplication,
                title = "Mega jogo galo",
                state = WindowState(size = DpSize.Unspecified)
            ) {
                App(driver, ::exitApplication)
            }
        }
    }



//@Composable
//@Preview
//fun testPlayerView(){
//    PlayerView(Player.X)
//}