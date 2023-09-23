package com.functional.chapter2

inline infix fun <I1, I2, O> ((I1)->I2).andThen(crossinline block: (I2) -> O): (I1) -> O = {
    block(this(it))
}
