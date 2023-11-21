package com.functional.chapter7

inline fun <T> tryAndCatch(block: () -> T): Outcome<ThrowableError, T> = try {
    block().asSuccess()
} catch (e: Exception) {
    ThrowableError(e).asFailure()
}

data class ThrowableError(val t: Throwable) : OutcomeError {
    override val msg: String = t.message.orEmpty()
}
