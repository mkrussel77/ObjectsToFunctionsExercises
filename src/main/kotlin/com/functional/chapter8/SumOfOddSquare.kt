package com.functional.chapter8

fun Long.isOdd(): Boolean = this % 2 != 0L

fun sumOfOddSquaresForLoop(numbers: List<Long>): Long {
    var tot = 0L
    for (i in numbers) {
        if (i.isOdd()) tot += i * i
    }
    return tot
}

@Suppress("SimplifiableCallChain") // testing performance difference
fun sumOfOddSquaresList(numbers: List<Long>): Long = numbers
    .filter(Long::isOdd)
    .map { it * it }
    .sum()

fun sumOfOddSquaresSequence(numbers: Sequence<Long>): Long = numbers
    .filter(Long::isOdd)
    .map { it * it }
    .sum()

fun sumOfOddSquaresListBy(numbers: List<Long>): Long = numbers
    .filter(Long::isOdd)
    .sumOf { it * it }

fun sumOfOddSquaresSequenceBy(numbers: Sequence<Long>): Long = numbers
    .filter(Long::isOdd)
    .sumOf { it * it }

fun sumOfOddSquaresSumOf(numbers: List<Long>) = numbers.sumOf { if (it.isOdd()) it * it else 0 }
fun sumOfOddSequenceSumOf(numbers: Sequence<Long>) =
    numbers.sumOf { if (it.isOdd()) it * it else 0 }
