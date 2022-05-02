package app

import Alice
import Bob
import ExampleProtocol_Alice_1
import ExampleProtocol_Bob_1
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.david.sessionkotlin.backend.SKBuffer
import org.david.sessionkotlin.backend.SKMPEndpoint
import org.david.sessionkotlin.backend.channel.SKChannel

fun main() {
    runBlocking {
        val chan = SKChannel()
        // Alice
        launch {
            SKMPEndpoint().use { e ->
                e.connect(Bob, chan)

                val buf = SKBuffer<Int>()

                ExampleProtocol_Alice_1(e)
                    .sendToBob(1)
                    .receiveFromBob(buf)

                println("Final value: ${buf.value}")
            }
        }

        // Bob
        launch {
            SKMPEndpoint().use { e ->
                e.connect(Alice, chan)

                val buf = SKBuffer<Int>()

                ExampleProtocol_Bob_1(e)
                    .receiveFromAlice(buf)
                    .sendToAlice(buf.value + 1)
            }
        }
    }
}
