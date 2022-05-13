package app

import Alice
import Bob
import ExampleProtocolAlice1
import ExampleProtocolBob1
import com.github.d_costa.sessionkotlin.backend.SKBuffer
import com.github.d_costa.sessionkotlin.backend.channel.SKChannel
import com.github.d_costa.sessionkotlin.backend.endpoint.SKMPEndpoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val chan = SKChannel()
        // Alice
        launch {
            SKMPEndpoint().use { e ->
                e.connect(Bob, chan)

                val buf = SKBuffer<Int>()

                ExampleProtocolAlice1(e)
                    .sendToBob(1)
                    .receiveFromBob(buf)
                    .also { println("Alice received ${buf.value} from Bob") }
                    .sendToBob("Hello")
            }
        }

        // Bob
        launch {
            SKMPEndpoint().use { e ->
                e.connect(Alice, chan)

                val buf = SKBuffer<Int>()
                val strBuf = SKBuffer<String>()

                ExampleProtocolBob1(e)
                    .receiveFromAlice(buf)
                    .also { println("Bob received ${buf.value} from Alice") }
                    .sendToAlice(buf.value * 2)
                    .receiveFromAlice(strBuf)
                    .also { println("Bob received ${strBuf.value} from Alice") }
            }
        }
    }
}
