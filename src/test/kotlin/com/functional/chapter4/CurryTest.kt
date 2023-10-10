package com.functional.chapter4

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class CurryTest {
    private fun sum(num1: Int, num2: Int) = num1 + num2
    private fun strConcat(s1: String, s2: String) = "$s1 $s2"

    @Test
    fun `plus three fun`() {
        val plus3Fn = ::sum.curry()(3)
        expectThat(plus3Fn(4)).isEqualTo(7)
    }

    @Test
    fun `star prefix`() {
        val starPrefixFn = ::strConcat.curry()("*")
        expectThat(starPrefixFn("abc")).isEqualTo("* abc")
    }

    @Test
    fun `concat with +++`() {
        val curriedConcat = ::strConcat.curry()
        expectThat(curriedConcat `+++` "head" `+++` "tail")
            .isEqualTo("head tail")
    }

    @Test
    fun `sum with +++`() {
        val curriedSum = ::sum.curry()
        expectThat(curriedSum `+++` 4 `+++` 5).isEqualTo(9)
    }
}
