package com.functional.chapter9

data class ContextReader<CTX, out T>(val runWith: (CTX) -> T) {
    fun <U> transform(f: (T) -> U): ContextReader<CTX, U> =
        ContextReader { ctx -> f(runWith(ctx)) }

    fun <U> bind(f: (T) -> ContextReader<CTX, U>): ContextReader<CTX, U> =
        transform(f).join()
}

fun <CTX, T> ContextReader<CTX, ContextReader<CTX, T>>.join(): ContextReader<CTX, T> =
    ContextReader { ctx ->
        runWith(ctx).runWith(ctx)
    }
