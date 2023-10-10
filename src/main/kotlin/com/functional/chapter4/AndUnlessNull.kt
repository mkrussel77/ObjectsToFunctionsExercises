package com.functional.chapter4

import com.functional.chapter2.FUN

infix fun <A: Any, B: Any, C: Any> FUN<A, B?>.andUnlessNull( other: FUN<B, C?>): FUN<A, C?> = {
    this(it)?.let(other)
}
