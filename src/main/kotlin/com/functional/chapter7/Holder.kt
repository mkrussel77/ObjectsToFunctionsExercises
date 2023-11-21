package com.functional.chapter7

data class Holder<T>(@PublishedApi internal val value: T) {
    inline fun <U> transform(f: (T) -> U): Holder<U> = Holder(f(value))

    inline fun combine(other: Holder<T>, f: (T, T) -> T): Holder<T> =
        transform { f(it, other.value) }

    companion object {
        inline fun <T, U> lift(crossinline f: (T) -> U): (Holder<T>) -> Holder<U> =
            { c: Holder<T> -> c.transform(f) }
    }
}
