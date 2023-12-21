package isel.tds.galo.demo

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        var clickable by remember { mutableStateOf(true) }
        Row {
            Button(enabled = clickable, onClick = {
                println("Clicked")
                clickable = false
                repeat(5) { print('.'); Thread.sleep(1000) }
                clickable = true
            }) { Text("Click me") }
            Button(enabled = !clickable, onClick = {
                clickable = true
            }) { Text("Enable Click") }
        }
    }
}