package app

import com.github.sessionkotlin.lib.dsl.SKRole
import com.github.sessionkotlin.lib.dsl.globalProtocol

fun main() {
    val a = SKRole("Alice")
    val b = SKRole("Bob")
    val c = SKRole("Charlie")

    globalProtocol("Protocol", callbacks = true) {
        choice(a) {
            branch {
                send<Int>(a, b, "A1", "A1 > 0")
                send<Long>(b, c, "A2")
            }
            branch {
                send<Int>(a, b, "B1")
                send<String>(b, c, "B2")
            }
        }
    }
}
