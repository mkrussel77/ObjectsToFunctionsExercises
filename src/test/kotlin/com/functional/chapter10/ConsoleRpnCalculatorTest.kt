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

class ConsoleRpnCalculatorTest {
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
    fun `reads and writes from console`() {
        val output = ByteArrayOutputStream()
        val input = ByteArrayInputStream(
            """
            4 3 2 1 - + *
            1 2 3 * 4 - +
            Q
        """.trimIndent().toByteArray(Charsets.UTF_8)
        )

        System.setIn(input)
        System.setOut(PrintStream(output))

        consoleRpnCalculator().runWith(SystemConsole())

        val expected =
            """
              Write an RPN expression to calculate the result or Q to quit.
              The result is: 16.0
              Write an RPN expression to calculate the result or Q to quit.
              The result is: 3.0
              Write an RPN expression to calculate the result or Q to quit.
              Bye!
              
              """.trimIndent()
        expectThat(output.toString(Charsets.UTF_8)).isEqualTo(expected)
    }
}
