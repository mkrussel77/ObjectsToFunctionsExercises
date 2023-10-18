package com.functional.chapter5

fun Int.collatz() = collatzR(listOf(), this)
tailrec fun collatzR(acc: List<Int>, x: Int): List<Int> = when {
    x <= 1 -> acc + x
    x % 2 == 0 -> collatzR(acc + x, x / 2)
    else -> collatzR(acc + x, x * 3 + 1)
}
