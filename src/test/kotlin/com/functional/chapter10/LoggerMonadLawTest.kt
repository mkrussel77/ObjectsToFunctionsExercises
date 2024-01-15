package com.functional.chapter10

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.random.Random

class LoggerMonadLawTest {
    val a = Random.nextInt()

    @Test
    fun `left identity`() {
        val f: (Int) -> Logger<Int> = { x -> Logger(x * 2, listOf("foo")) }
        val ma = Logger(a, emptyList()).bind(f)

        expectThat(ma).isEqualTo(f(a))
    }

    @Test
    fun `right identity`() {
        val ma = Logger(a, listOf("foo")).bind { Logger(it, emptyList()) }
        expectThat(ma).isEqualTo(Logger(a, listOf("foo")))
    }

    @Test
    fun associativity() {
        val f: (Int) -> Logger<Int> = { Logger(it * 2, listOf("foo")) }
        val g: (Int) -> Logger<Int> = { Logger(it + 5, listOf("bar")) }

        val ma1 = Logger(a, emptyList()).bind(f).bind(g)
        val ma2 = Logger(a, emptyList()).bind { x -> f(x).bind(g) }

        expectThat(ma1).isEqualTo(ma2)
    }
}
