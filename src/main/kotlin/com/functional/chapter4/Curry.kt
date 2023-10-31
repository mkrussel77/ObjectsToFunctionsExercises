package com.functional.chapter4

import com.functional.chapter2.FUN

fun <A, B, C> ((A, B) -> C).curry(): (A) -> (B) -> C = { a ->
    { b ->
        this(a, b)
    }
}

infix fun <A, B> FUN<A, B>.`+++`(a: A): B = this(a)
