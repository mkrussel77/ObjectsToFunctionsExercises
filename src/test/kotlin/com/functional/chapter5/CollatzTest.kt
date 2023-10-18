package com.functional.chapter5

import com.functional.positiveInts
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.last

class CollatzTest {
    @Test
    fun `collatz produces correct sequence for 13`() {
        expectThat(13.collatz()).isEqualTo(listOf(13, 40, 20, 10, 5, 16, 8, 4, 2, 1))
    }

    @Test
    fun `collatz produces correct sequence for 8`() {
        expectThat(8.collatz()).isEqualTo(listOf(8, 4, 2, 1))
    }

    @Test
    fun `collatz resolves to 1 for positive numbers`() {
        // limit numbers to avoid overflows
        positiveInts(1_000)
            .take(100)
            .forEach {
                expectThat(it.collatz()).last().isEqualTo(1)
            }
    }
}
