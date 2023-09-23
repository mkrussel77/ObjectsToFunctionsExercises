package com.functional.chapter2

class FunStack<E> private constructor(val elements: List<E>) {
    constructor() : this(emptyList())

    fun push(element: E): FunStack<E> = FunStack(elements + element)
    fun pop(): Pair<E, FunStack<E>> {
        return elements.last() to FunStack(elements.dropLast(1))
    }
    fun size(): Int = elements.size
}
