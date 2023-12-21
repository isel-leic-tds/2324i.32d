package isel.tds.galo.demo

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun log(lab: String) = println("$lab: ${Thread.currentThread().name}")


fun main() {
    log("main")
    application(exitProcessOnExit = false) {
        log("application")
        Window(onCloseRequest = ::exitApplication) {
            Button(onClick = { log("onClick") }) { Text("Ok") }
        }
    }
    log("exit")
}
