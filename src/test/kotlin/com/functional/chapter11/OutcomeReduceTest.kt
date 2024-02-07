package com.functional.chapter11

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class OutcomeReduceTest {
    @Test
    fun `reduceSuccess with all successes shall merge values`() {
        val list = listOf(
            1.asSuccess(),
            2.asSuccess(),
            3.asSuccess()
        )

        val result = list.reduceSuccess { acc: Int, next: Int ->
            acc + next
        }

        expectThat(result).isEqualTo((1 + 2 + 3).asSuccess())
    }

    @Test
    fun `reduceSuccess with errors shall return first errors`() {
        val list = listOf(
            1.asSuccess(),
            BaseError("An error occurred").asFailure(),
            3.asSuccess(),
            BaseError("Other error occurred").asFailure(),
        )

        val result: Outcome<OutcomeError, Int> = list.reduceSuccess { acc: Int, next: Int ->
            acc + next
        }

        expectThat(result).isEqualTo(BaseError("An error occurred").asFailure())
    }

    data class BaseError(override val msg: String) : OutcomeError
}