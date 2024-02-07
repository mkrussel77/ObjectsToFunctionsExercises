package com.functional.chapter11


fun <E : OutcomeError, S, T : S> List<Outcome<E, T>>.reduceSuccess(f: (S, T) -> T): Outcome<E, S> =
    reduce { acc, nextOutcome ->
        acc.bind { t ->
            nextOutcome.transform { s -> f(s, t) }
        }
    }
