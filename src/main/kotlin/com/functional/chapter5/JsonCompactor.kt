package com.functional.chapter5

sealed class JsonCompactor {
    abstract val jsonCompacted: String
    abstract fun compact(c: Char): JsonCompactor
}

data class InQuotes(override val jsonCompacted: String) : JsonCompactor() {
    override fun compact(c: Char): JsonCompactor = when (c) {
        '"' -> OutQuotes(jsonCompacted + c)
        '\\' -> Escaped(jsonCompacted + c)
        else -> InQuotes(jsonCompacted + c)
    }
}

data class OutQuotes(override val jsonCompacted: String) : JsonCompactor() {
    override fun compact(c: Char): JsonCompactor = when {
        c.isWhitespace() -> this
        c == '"' -> InQuotes(jsonCompacted + c)
        else -> OutQuotes(jsonCompacted + c)
    }
}

data class Escaped(override val jsonCompacted: String) : JsonCompactor() {
    override fun compact(c: Char): JsonCompactor = InQuotes(jsonCompacted + c)
}

fun compactJson(jsonText: String): String = jsonText.fold<JsonCompactor>(OutQuotes("")) { acc, c ->
    acc.compact(c)
}.jsonCompacted
