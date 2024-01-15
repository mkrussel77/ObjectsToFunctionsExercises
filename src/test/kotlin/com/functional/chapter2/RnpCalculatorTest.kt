package com.functional.chapter2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RnpCalculatorTest {
    @Test
    fun `test add`() {
        assertEquals(9.0, calc("4 5 +"))
    }

    @Test
    fun `test divide`() {
        assertEquals(3.0, calc("6 2 /"))
    }

    @Test
    fun `test multiple operators at end`() {
        assertEquals(10.0, calc("5 6 2 1 + / *"))
    }

    @Test
    fun `test operators in middle`() {
        assertEquals(2.0, calc("2 5 * 4 + 3 2 * 1 + /"))
    }
}
