package isel.tds.galo.storage

import isel.tds.galo.model.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.deleteRecursively

@TestInstance(Lifecycle.PER_CLASS)
class TextFileStorageTest {

    @OptIn(ExperimentalPathApi::class)
    @AfterAll
    @BeforeAll
    fun cleanup() {
        val path = Path("storage")
        path.deleteRecursively()
    }
    @Test
    fun create() {
        val textFileStorage = TextFileStorage<String, String>("storage",
            object : Serializer<String> {
            override fun serialize(data: String): String = data
            override fun deserialize(text: String): String = text
        })
        textFileStorage.create("key1", "dataFromKey1")

        val readData = textFileStorage.read("key1")
        assertEquals("dataFromKey1", readData)
    }

    @Test
    fun createBoard() {
        val textFileStorage = TextFileStorage<String, Board>("storage", BoardSerializer)

        val sut = BoardRun( mapOf(
            Position(0) to Player.O, Position(1) to Player.X, Position(2) to Player.O,
            Position(3) to Player.X, Position(4) to Player.X, Position(5) to Player.O,
            Position(6) to Player.O
        ),
            turn = Player.X
        ).play(Position(8))

        textFileStorage.create("Board1", sut)

        val readData = textFileStorage.read("Board1")
        assertEquals(sut, readData)
    }
}