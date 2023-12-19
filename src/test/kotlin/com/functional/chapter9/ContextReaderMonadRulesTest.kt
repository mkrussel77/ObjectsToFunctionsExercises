package com.functional.chapter9

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ContextReaderMonadRulesTest {
    @Test
    fun `left identity`() {
        val initialValue = 10
        val contextReader = ContextReader { _: Int -> initialValue }
        val left = contextReader.bind(this::sum)
        expectThat(left.runWith(0)).isEqualTo(sum(initialValue).runWith(0))
    }

    @Test
    fun `right identity`() {
        val initialValue = 10
        val contextReader = ContextReader { _: Int -> initialValue }
        val right = contextReader.bind { value -> ContextReader { value } }

        expectThat(right.runWith(0)).isEqualTo(contextReader.runWith(0))
    }

    @Test
    fun associativity() {
        val sumOperand = 10
        val contextReader = ContextReader { _: Int -> sumOperand }

        val cr1 = contextReader.bind(::sum).bind(::product)
        val cr2 = contextReader.bind { value -> sum(value).bind(::product) }

        expectThat(cr1.runWith(2)).isEqualTo(cr2.runWith(2))
    }

    private fun sum(operand: Int): ContextReader<Int, Int> = ContextReader { it + operand }
    private fun product(operand: Int): ContextReader<Int, Int> = ContextReader { it * operand }
}
