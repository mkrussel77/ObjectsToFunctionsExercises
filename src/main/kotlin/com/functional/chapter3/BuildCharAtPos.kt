package com.functional.chapter3

fun buildCharAtPos(s: String): (Int) -> Char = { pos -> s[pos] }
