package com.functional.chapter4

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class EmailTemplateTest {
    private val michael = Person("Michael", "Krussel")
    private val uberto = Person("Uberto", "Barbini")

    private val templateText = """
        Dear {person},
        This is an email to you.
    """.trimIndent()

    private val emailTemplate = EmailTemplate(templateText)

    private val michaelExpected =  """
        Dear Michael,
        This is an email to you.
    """.trimIndent()

    private val ubertoExpected =  """
        Dear Uberto,
        This is an email to you.
    """.trimIndent()

    @Test
    fun `simple template`() {
        expectThat(emailTemplate(michael)).isEqualTo(michaelExpected)
        expectThat(emailTemplate(uberto)).isEqualTo(ubertoExpected)
    }
}
