package com.functional.chapter7

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.startsWith
import java.time.LocalDate

class TryCatchTest {
    @Test
    fun `exception is caught`() {
        expectThat(todayGreetings("12/3/2020"))
            .startsWith("Error parsing the date")
    }

    @Test
    fun `success is returned`() {
        expectThat(todayGreetings("2020-03-12"))
            .isEqualTo("Today is 2020-03-12")
    }

    private fun todayGreetings(dateString: String) = tryAndCatch { LocalDate.parse(dateString) }
        .transform { "Today is $it" }
        .recover { "Error parsing the date ${it.msg}" }
}
