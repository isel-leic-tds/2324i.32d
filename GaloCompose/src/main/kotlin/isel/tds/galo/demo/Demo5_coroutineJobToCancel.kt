package isel.tds.galo.demo

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        val scope = rememberCoroutineScope()
        var clickable by remember { mutableStateOf(true) }
        var job by remember { mutableStateOf<Job?>(null) }
        Row {
            Button(enabled = clickable, onClick = {
                println("Clicked")
                clickable = false
                job = scope.launch {
                    repeat(5) { print('.'); delay(1000) }
                    clickable = true
                    job = null
                }
            }) { Text("Click me") }
            Button(enabled = !clickable, onClick = {
                clickable = true
                job?.cancel()
                job=null
            }) { Text("Enable Click") }
        }
    }
}
