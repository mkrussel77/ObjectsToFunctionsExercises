package com.functional.chapter11

import com.functional.chapter9.ContextReader
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

typealias ConfigurationReader = ContextReader<Map<String, String>, String>

class ApplicativeReaderTest {
    private val number = ConfigurationReader { ctx -> ctx["number"].orEmpty() }
    private val street = ConfigurationReader { ctx -> ctx["street"].orEmpty() }
    private val city = ConfigurationReader { ctx -> ctx["city"].orEmpty() }

    private fun address(number: String, street: String, city: String): String =
        "$number $street, $city"

    private fun streetAddress(number: String, street: String): String = "$number $street"

    private val data = mapOf("number" to "10", "street" to "Downing Street", "city" to "London")

    @Test
    fun `read and apply configuration values correctly with two args`() {
        val res = ::address `!` number `*` street `*` city

        expectThat(res.runWith(data)).isEqualTo("10 Downing Street, London")
    }

    @Test
    fun `read and apply configuration values correctly with three args`() {
        val res = ::streetAddress `!` number `*` street

        expectThat(res.runWith(data)).isEqualTo("10 Downing Street")
    }
}
