package app


import com.github.sessionkotlin.lib.backend.SKBuffer
import com.github.sessionkotlin.lib.backend.channel.SKChannel
import com.github.sessionkotlin.lib.backend.endpoint.SKMPEndpoint
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import protocol.Alice
import protocol.Bob
import protocol.Charlie
import protocol.callbacks.*
import protocol.fluent.*


fun main() {
    fluent()
    callbacks()
}

fun fluent() {
    println("--- Fluent API ---")


    runBlocking {
        // Plain channel for Charlie to send a port number to Bob
        // to avoid hard coding the port number.
        val portChan = Channel<Int>()

        val chan = SKChannel(Alice, Bob) // Communication channel (Optionally defined participants)

        // Alice
        launch {
            SKMPEndpoint().use { e ->
                e.connect(Bob, chan)
                ProtocolAlice1(e).sendA1ToBob(1)
            }
        }

        // Bob
        launch {
            SKMPEndpoint().use { e ->
                e.connect(Alice, chan)
                e.request(Charlie, "localhost", portChan.receive())

                ProtocolBob1(e).branch()
                    .let { b ->
                        when (b) {
                            is ProtocolBob1_A1Interface -> {
                                var msg = 0
                                b
                                    .receiveA1FromAlice { msg = it }
                                    .sendA2ToCharlie(msg + 1L)
                            }

                            is ProtocolBob1_B1Interface -> {
                                val buf = SKBuffer<Int>()
                                b
                                    .receiveB1FromAlice(buf)
                                    .sendB2ToCharlie(buf.value.toString() + "abc")
                            }
                        }
                    }
            }
        }

        // Charlie
        launch {
            SKMPEndpoint().use { e ->
                e.connect(Alice, chan)

                val serverSocket = SKMPEndpoint.bind() // Bind to an available port
                portChan.send(serverSocket.port) // Send the port number to Bob before suspending on accept

                e.accept(Bob, serverSocket)

                ProtocolCharlie1(e).branch()
                    .let { b ->
                        when (b) {
                            is ProtocolCharlie1_A2Interface -> b.receiveA2FromBob { println("Charlie received $it") }
                            is ProtocolCharlie1_B2Interface -> b.receiveB2FromBob { println("Charlie received $it") }
                        }
                    }
                serverSocket.close()
            }
        }
    }
    println("------")
}

fun callbacks() {
    println("--- Callbacks API ---")

    runBlocking {
        // Plain channel for Charlie to send a port number to Bob
        // to avoid hard coding the port number.
        val portChan = Channel<Int>()

        val chan = SKChannel(Alice, Bob) // Communication channel (Optionally defined participants)

        // Alice
        launch {

            val callbacks = object : ProtocolAliceCallbacks {
                override fun onChoice1(): Choice1 = Choice1.Choice1_A1
                override fun sendA1ToBob(): Int = 1
                override fun sendB1ToBob(): Int = 2
            }
            ProtocolAliceCallbacksEndpoint(callbacks).use {
                it.connect(Bob, chan)
                it.start()
            }
        }

        // Bob
        launch {

            val callbacks = object : ProtocolBobCallbacks {
                var receivedMessage = 0
                override fun receiveA1FromAlice(v: Int) {
                    receivedMessage = v
                }

                override fun receiveB1FromAlice(v: Int) {
                    receivedMessage = v
                }

                override fun sendA2ToCharlie(): Long = receivedMessage + 1L

                override fun sendB2ToCharlie(): String = receivedMessage.toString() + "abc"
            }
            ProtocolBobCallbacksEndpoint(callbacks).use {
                it.connect(Alice, chan)
                it.request(Charlie, "localhost", portChan.receive())
                it.start()
            }
        }

        // Charlie
        launch {

            val callbacks = object : ProtocolCharlieCallbacks {
                override fun receiveA2FromBob(v: Long) {
                    println("Charlie received $v")
                }

                override fun receiveB2FromBob(v: String) {
                    println("Charlie received $v")
                }
            }
            ProtocolCharlieCallbacksEndpoint(callbacks).use {
                val serverSocket = SKMPEndpoint.bind() // Bind to an available port
                portChan.send(serverSocket.port) // Send the port number to Bob before suspending on accept

                it.accept(Bob, serverSocket)
                it.start()

                serverSocket.close()
            }
        }
    }

    println("------")
}