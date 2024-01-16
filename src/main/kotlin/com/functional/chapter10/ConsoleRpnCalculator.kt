package com.functional.chapter10

import com.functional.chapter2.calc
import com.functional.chapter9.ContextReader

private const val FAREWELL = "Bye!"

fun consoleRpnCalculator(): ContextReader<ConsoleContext, String> = ContextReader { ctx ->
    consoleRpnCalculator(ctx)
    FAREWELL
}

tailrec fun consoleRpnCalculator(ctx: ConsoleContext) {
    val msg = nextCalculation().runWith(ctx)
    if (msg != FAREWELL) {
        consoleRpnCalculator(ctx)
    }
}

private fun nextCalculation() =
    contextPrintln("Write an RPN expression to calculate the result or Q to quit.")
        .bind { _ -> contextReadln() }
        .bind { formula ->
            if (formula == "Q") {
                contextPrintln(FAREWELL)
            } else {
                contextPrintln("The result is: ${calc(formula)}")
            }
        }
