package com.functional.chapter5

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class FoldTest {
    @Test
    fun `fold elevators events`() {
        val values = listOf(Up, Up, Down, Up, Down, Down, Up, Up, Up, Down)
        val tot = values.fold(Elevator(0)) { acc, direction ->
            when (direction) {
                Up -> Elevator(acc.floor + 1)
                Down -> Elevator(acc.floor - 1)
            }
        }
        expectThat(tot).isEqualTo(Elevator(2))
    }
}
