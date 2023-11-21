package com.functional.chapter7

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class FunctorsCombineTest {
    @Test
    fun `combine shall create function that returns fucntions`() {
        val h = Holder("hello")
        val w = Holder("world")

        val result = h.combine(w){a,b -> "$a $b"}
        expectThat(result).isEqualTo(Holder("hello world"))
    }
}
