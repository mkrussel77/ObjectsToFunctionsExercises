package com.functional.chapter4

import com.functional.chapter2.FUN

fun <A, B, C> ((A, B) -> C).curry(): (A) -> (B) -> C = { a ->
    { b ->
        this(a, b)
    }
}

infix fun <B, C> FUN<B, C>.`+++`(b: B): C = this(b)
