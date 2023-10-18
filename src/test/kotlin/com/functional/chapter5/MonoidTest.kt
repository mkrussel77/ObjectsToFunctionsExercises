package com.functional.chapter5

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class MonoidTest {
    private val zeroMoney = Money(0.0)

    private data class Money(val amount: Double) {
        fun sum(other: Money) = Money(this.amount + other.amount)
    }

    @Test
    fun `fold int plus`() {
        with(Monoid(0, Int::plus)) {
            expectThat(listOf(1, 2, 3, 4, 10).fold()).isEqualTo(20)
        }
    }

    @Test
    fun `fold string plus`() {
        with(Monoid("", String::plus)) {
            expectThat(listOf("My", "Fair", "Lady").fold())
                .isEqualTo("MyFairLady")
        }
    }

    @Test
    fun `fold money`() {
        with(Monoid(zeroMoney, Money::sum)) {
            expectThat(listOf(Money(2.1), Money(3.9), Money(4.0)).fold())
                .isEqualTo(Money(10.0))
        }
    }
}
