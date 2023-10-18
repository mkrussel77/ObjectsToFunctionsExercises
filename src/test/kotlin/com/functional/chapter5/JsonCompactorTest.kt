package com.functional.chapter5

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class JsonCompactorTest {
    @Test
    fun `compactJson compacts text`() {
        val jsonText = """{ "my greetings" : "hello world! \"How are you?\"" }"""
        val expected = """{"my greetings":"hello world! \"How are you?\""}"""
        expectThat(compactJson(jsonText)).isEqualTo(expected)
    }
}
