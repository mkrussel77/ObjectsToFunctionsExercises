package com.functional.chapter8

data class NextFunction<T>(val list: List<T>): () -> T? {
    private val iterator = list.iterator()
    override fun invoke(): T? = if (iterator.hasNext()) iterator.next() else null
}
