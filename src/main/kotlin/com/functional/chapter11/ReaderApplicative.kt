package com.functional.chapter11

import com.functional.chapter9.ContextReader

@Suppress("DANGEROUS_CHARACTERS")
infix fun <CTX, A, B> ContextReader<CTX, (A) -> B>.`*`(a: ContextReader<CTX, A>): ContextReader<CTX, B> =
    andApply(a)

fun <CTX, A, B> ContextReader<CTX, (A) -> B>.andApply(a: ContextReader<CTX, A>): ContextReader<CTX, B> =
    transform2(this, a) { f, x -> f(x) }

fun <CTX, A, B, C> ((A, B) -> C).transformAndCurry(other: ContextReader<CTX, A>): ContextReader<CTX, (B) -> C> =
    other.transform { curry()(it) }

infix fun <CTX, A, B, C> ((A, B) -> C).`!`(other: ContextReader<CTX, A>): ContextReader<CTX, (B) -> C> =
    transformAndCurry(other)

infix fun <CTX, A, B, C, D> ((A, B, C) -> D).transformAndCurry(
    other: ContextReader<CTX, A>
): ContextReader<CTX, (B) -> (C) -> D> = other.transform { curry()(it) }


infix fun <CTX, A, B, C, D> ((A, B, C) -> D).`!`(other: ContextReader<CTX, A>): ContextReader<CTX, (B) -> (C) -> D> =
    transformAndCurry(other)

fun <CTX, A, B, R> transform2(
    first: ContextReader<CTX, A>,
    second: ContextReader<CTX, B>,
    f: (A, B) -> R
): ContextReader<CTX, R> = first.bind { a ->
    second.transform { b ->
        f(a, b)
    }
}
