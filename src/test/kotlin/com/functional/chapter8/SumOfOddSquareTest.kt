package com.functional.chapter8

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.time.Duration
import kotlin.time.measureTime

private const val PERFORMANCE_ITERATIONS = 100

class SumOfOddSquareTest {
    private val numbers = (1..1_000_000L).toList()
    private val numbersSeq = numbers.asSequence()

    @Test
    fun `sum is correct`() {
        expectThat(sumOfOddSquaresForLoop(numbers)).isEqualTo(166666666666500000)
        expectThat(sumOfOddSquaresList(numbers)).isEqualTo(166666666666500000)
        expectThat(sumOfOddSquaresSequence(numbersSeq)).isEqualTo(166666666666500000)
        expectThat(sumOfOddSquaresListBy(numbers)).isEqualTo(166666666666500000)
        expectThat(sumOfOddSquaresSequenceBy(numbersSeq)).isEqualTo(166666666666500000)
        expectThat(sumOfOddSquaresSumOf(numbers)).isEqualTo(166666666666500000)
        expectThat(sumOfOddSequenceSumOf(numbersSeq)).isEqualTo(166666666666500000)
    }

    @Test
    @Disabled("too slow, run manually when interested")
    fun performanceTest() {
        // warmup
        repeat(500) {
            sumOfOddSquaresForLoop(numbers)
            sumOfOddSquaresList(numbers)
            sumOfOddSquaresSequence(numbersSeq)
            sumOfOddSquaresListBy(numbers)
            sumOfOddSquaresSequenceBy(numbersSeq)
            sumOfOddSquaresSumOf(numbers)
            sumOfOddSequenceSumOf(numbersSeq)
        }

        var sumOfOddSquaresForLoopDuration: Duration = Duration.ZERO
        var sumOfOddSquaresListDuration: Duration = Duration.ZERO
        var sumOfOddSquaresSequenceDuration: Duration = Duration.ZERO
        var sumOfOddSquaresListByDuration: Duration = Duration.ZERO
        var sumOfOddSquaresSequenceByDuration: Duration = Duration.ZERO
        var sumOfOddSquaresListSumOfDuration: Duration = Duration.ZERO
        var sumOfOddSquaresSequenceSumOfDuration: Duration = Duration.ZERO

        repeat(PERFORMANCE_ITERATIONS) {
            sumOfOddSquaresForLoopDuration += measureTime { sumOfOddSquaresForLoop(numbers) }
            sumOfOddSquaresListDuration += measureTime { sumOfOddSquaresList(numbers) }
            sumOfOddSquaresSequenceDuration += measureTime { sumOfOddSquaresSequence(numbersSeq) }
            sumOfOddSquaresListByDuration += measureTime { sumOfOddSquaresListBy(numbers) }
            sumOfOddSquaresSequenceByDuration += measureTime { sumOfOddSquaresSequenceBy(numbersSeq) }
            sumOfOddSquaresListSumOfDuration += measureTime { sumOfOddSquaresSumOf(numbers) }
            sumOfOddSquaresSequenceSumOfDuration += measureTime { sumOfOddSequenceSumOf(numbersSeq) }
        }

        println("Avg time with for loop: ${sumOfOddSquaresForLoopDuration / PERFORMANCE_ITERATIONS}")
        println("Avg time with for list: ${sumOfOddSquaresListDuration / PERFORMANCE_ITERATIONS}")
        println("Avg time with for sequence: ${sumOfOddSquaresSequenceDuration / PERFORMANCE_ITERATIONS}")
        println("Avg time with for list by: ${sumOfOddSquaresListByDuration / PERFORMANCE_ITERATIONS}")
        println("Avg time with for sequence by: ${sumOfOddSquaresSequenceByDuration / PERFORMANCE_ITERATIONS}")
        println("Avg time with for list sum of: ${sumOfOddSquaresListSumOfDuration / PERFORMANCE_ITERATIONS}")
        println("Avg time with for sequence sum of: ${sumOfOddSquaresSequenceSumOfDuration / PERFORMANCE_ITERATIONS}")
    }
}
