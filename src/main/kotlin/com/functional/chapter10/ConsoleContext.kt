package com.functional.chapter10

import com.functional.chapter9.ContextReader

interface ConsoleContext {
    fun printLine(msg: String): String
    fun readLine(): String
}

class SystemConsole : ConsoleContext {
    override fun printLine(msg: String): String = msg.also(::println)
    override fun readLine(): String = readln()
}


fun contextPrintln(msg: String) =
    ContextReader<ConsoleContext, String>{ ctx -> ctx.printLine(msg) }
fun contextReadln() =
    ContextReader<ConsoleContext, String>{ ctx -> ctx.readLine() }

fun main() {
    contextPrintln("Hello, what's your name?")
        .bind { _ -> contextReadln() }
        .bind { name -> contextPrintln("Hello, $name") } .runWith(SystemConsole())
}
