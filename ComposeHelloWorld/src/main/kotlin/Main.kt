import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App() {
    println("App")
    var text by remember { mutableStateOf("Hello, World!") }
    var text2 by remember { mutableStateOf("Hello, World2!") }
//    var text by mutableStateOf("Hello, World!")
    var counter = 0
    var counter2 = 0

    MaterialTheme {
        Column {
            Row {
                Button(onClick = {
                    text = "Hello, Desktop!" + counter++
                }) {
                    println("Button")
                    Text(text)
                }
            }
            Row {
                Button(onClick = {
                    text2 = "Hello, Desktop!" + counter2++
                }) {
                    println("Button")
                    Text(text2)
                }
            }
            Row {
                Button(onClick = {
                    text = "Hello, Desktop!" + counter++
                }) {
                    println("Button")
                    Text(text)
                }
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
