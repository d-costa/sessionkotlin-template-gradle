package app

import org.david.sessionkotlin.dsl.SKRole
import org.david.sessionkotlin.dsl.globalProtocol

fun main() {
    val a = SKRole("Alice")
    val b = SKRole("Bob")

    globalProtocol("ExampleProtocol") {
        send<Int>(a, b, "val1")
        send<Int>(b, a, "val2")
    }
}
