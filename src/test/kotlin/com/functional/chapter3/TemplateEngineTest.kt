package com.functional.chapter3

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class TemplateEngineTest {
    @Test
    fun `test basic template`() {
        val template = """
            Happy Birthday {name} {surname}!
            from {sender}.
        """.trimIndent()

        val data = mapOf("name" tag "Uberto", "surname" tag "Barbini", "sender" tag "PragProg")

        val actual = renderTemplate(template, data)
        val expected = """
            Happy Birthday Uberto Barbini!
            from PragProg.
        """.trimIndent()

        expectThat(actual).isEqualTo(expected)
    }
}
