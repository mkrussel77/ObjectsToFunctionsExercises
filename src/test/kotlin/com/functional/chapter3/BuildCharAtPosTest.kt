package com.functional.chapter3

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class BuildCharAtPosTest {
    @Test
    fun `test first char`() {
        val myCharAtPos = buildCharAtPos("Kotlin")

        expectThat(myCharAtPos(0)).isEqualTo('K')
    }
}
