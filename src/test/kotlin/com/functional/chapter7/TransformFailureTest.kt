package com.functional.chapter7

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class TransformFailureTest {
    @Test
    fun `transform failure happy path`() {
        val res = sendEmail("myfile.txt")

        expectThat(res).isEqualTo(Unit.asSuccess())
    }

    @Test
    fun `receive correct failure`() {
        val res = sendEmail("errorfile.txt")

        expectThat(res)
            .isEqualTo(EmailError("error reading file errorfile.txt missing!").asFailure())
    }
}
