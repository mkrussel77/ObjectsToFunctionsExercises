package com.functional.chapter7

interface OutcomeError {
    val msg: String
}

sealed class Outcome<out E : OutcomeError, out T> {

    inline fun <U> transform(f: (T) -> U): Outcome<E, U> =
        when (this) {
            is Success -> f(value).asSuccess()
            is Failure -> this
        }

    inline fun <F : OutcomeError> transformFailure(f: (E) -> F): Outcome<F, T> = when (this) {
        is Success -> this
        is Failure -> f(error).asFailure()
    }
}

data class Failure<E : OutcomeError> internal constructor(val error: E) : Outcome<E, Nothing>()
data class Success<T> internal constructor(val value: T) : Outcome<Nothing, T>()

inline fun <T, E : OutcomeError> Outcome<E, T>.onFailure(exitBlock: (E) -> Nothing): T =
    when (this) {
        is Success<T> -> value
        is Failure<E> -> exitBlock(error)
    }

inline fun <T,E:OutcomeError> Outcome<E,T>.recover(recoverError: (E)->T): T =
    when (this) {
        is Success -> value
        is Failure -> recoverError(error)
    }

fun <T> T.asSuccess(): Outcome<Nothing, T> = Success(this)
fun <E : OutcomeError> E.asFailure(): Outcome<E, Nothing> = Failure(this)
