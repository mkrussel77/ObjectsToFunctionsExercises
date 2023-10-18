package com.functional.chapter5

data class Monoid<T>(
    val zero: T,
    val combination: T.(T) -> T
) {
    fun Iterable<T>.fold(): T = fold(zero, combination)
}
