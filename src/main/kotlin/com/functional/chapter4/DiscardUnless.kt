package com.functional.chapter4

fun <T> T.discardUnless(predicate: T.() -> Boolean): T? = if (this.predicate()) this else null
