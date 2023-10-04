package com.functional.chapter3


@JvmInline
value class StringTag(val text: String)

infix fun String.tag(value: String): Pair<String, StringTag> = "{$this}" to StringTag(value)
fun renderTemplate(template: String, data: Map<String, StringTag>) =
    data.entries.fold(template) { acc, (tagName, tag) ->
        acc.replace(tagName, tag.text)
    }
