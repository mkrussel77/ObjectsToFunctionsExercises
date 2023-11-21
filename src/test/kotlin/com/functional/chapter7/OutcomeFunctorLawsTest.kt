package com.functional.chapter7

import com.functional.chapter2.andThen
import com.functional.intGenerator
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class OutcomeFunctorLawsTest {
    @Test
    fun `identity with success returns equal outcomes`() {
        intGenerator()
            .take(1000)
            .forEach {
                val outcome: Outcome<TestError, Int> = it.asSuccess()
                expectThat(outcome.transform(::identity)).isEqualTo(outcome)
            }
    }

    @Test
    fun `identity with failure returns equal outcome`() {
        val outcome: Outcome<TestError, Int> = TestError("test").asFailure()
        expectThat(outcome.transform(::identity)).isEqualTo(outcome)
    }

    @Test
    fun `morphism composition`() {
        val addTen = { value: Int -> value + 10 }
        val doubleValue = { value: Int -> value * 2 }
        val addTenAndDouble = addTen andThen doubleValue

        intGenerator()
            .take(1000)
            .forEach {
                val outcome = it.asSuccess()
                expectThat(outcome.transform(addTenAndDouble)).isEqualTo(
                    outcome.transform(addTen).transform(doubleValue)
                )
            }
    }
}

data class TestError(override val msg: String) : OutcomeError
