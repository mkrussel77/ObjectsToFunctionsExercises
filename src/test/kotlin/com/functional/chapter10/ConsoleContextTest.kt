package com.functional.chapter10

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.PrintStream

class ConsoleContextTest {
    private lateinit var initialInput: InputStream
    private lateinit var initialOutput: PrintStream

    @BeforeEach
    fun setUp() {
        initialInput = System.`in`
        initialOutput = System.out
    }

    @AfterEach
    fun tearDown() {
        System.setIn(initialInput)
        System.setOut(initialOutput)
    }

    @Test
    fun greet() {
        val output = ByteArrayOutputStream()
        System.setIn(ByteArrayInputStream("Michael".toByteArray(Charsets.UTF_8)))
        System.setOut(PrintStream(output))

        contextPrintln("Hello, what's your name?")
            .bind { _ -> contextReadln() }
            .bind { name -> contextPrintln("Hello, $name") }.runWith(SystemConsole())

        val expected = """
            Hello, what's your name?
            Hello, Michael
            
        """.trimIndent()

        val outputString = output.toString(Charsets.UTF_8)
        expectThat(outputString).isEqualTo(expected)
    }
}
