import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import isel.tds.galo.model.Player

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
//        Button(onClick = {
//            text = "Hello, Desktop!"
//        }) {
//            Text(text)
//        }
        PlayerView(Player.X)
    }
}

@Composable
fun PlayerView(player: Player?){

    val modifier = Modifier.size(100.dp).background(color = Color.Yellow)
    if( player == null) {
        Box(modifier)
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
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
