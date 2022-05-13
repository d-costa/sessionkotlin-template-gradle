package app

import com.github.d_costa.sessionkotlin.dsl.GlobalProtocol
import com.github.d_costa.sessionkotlin.dsl.SKRole
import com.github.d_costa.sessionkotlin.dsl.globalProtocol

fun main() {
    val a = SKRole("Alice")
    val b = SKRole("Bob")

    val auxProtocol: GlobalProtocol = {
        send<String>(a, b, "stringValue", "stringValue != ''")
    }

    globalProtocol("ExampleProtocol", callbacks = true) {
        send<Int>(a, b, "val1")
        send<Int>(b, a, "val2", "val2 > val1")
        auxProtocol()
    }
}
