package com.functional.chapter10


data class Logger<T>(val value: T, val log: List<String>) {
    fun <U> transform(f: (T) -> U): Logger<U> = Logger(f(value), log)
    fun <U> bind(f: (T) -> Logger<U>): Logger<U> = f(value).let { it.copy(log = log + it.log) }
}
