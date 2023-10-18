package com.functional

import kotlin.random.Random

fun intGenerator(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Sequence<Int> =
    generateSequence {
        Random.nextInt(min, max)
    }

fun positiveInts(max: Int = Int.MAX_VALUE) = intGenerator(min = 1, max = max)
