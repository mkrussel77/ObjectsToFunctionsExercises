package com.functional.chapter4

import com.functional.chapter3.renderTemplate
import com.functional.chapter3.tag

data class Person(val firstName: String, val lastName: String)

class EmailTemplate(private val templateText: String) : (Person) -> String {
    override fun invoke(aPerson: Person): String =
        renderTemplate(templateText, mapOf("person" tag aPerson.firstName))
}
