package isel.tds.galo.demo

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        val scope = rememberCoroutineScope()
        var clickable by remember { mutableStateOf(true) }
        Row {
            log("Recompose Row")
            Button(enabled = clickable, onClick = {
                log("Clicked 1")
                clickable = false
                scope.launch {
                    repeat(5) { print('.'); delay(1000) }
                    log("timedout")
                    clickable = true
                }
            }) { Text("Click me") }
            Button(enabled = !clickable, onClick = {
                log("Clicked 2")
                clickable = true
            }) { Text("Enable Click") }
        }
    }
}