package com.functional.chapter10

import com.functional.chapter2.calc
import com.functional.chapter9.ContextReader

fun consoleRpnCalculator(): ContextReader<ConsoleContext, String> = ContextReader { ctx ->
    consoleRpnCalculator(ctx)
    "Bye!"
}

tailrec fun consoleRpnCalculator(ctx: ConsoleContext) {
    val msg = contextPrintln("Write an RPN expression to calculate the result or Q to quit.")
        .bind { _ -> contextReadln() }
        .bind { formula ->
            if (formula == "Q") {
                contextPrintln("Bye!")
            } else {
                contextPrintln("The result is: ${calc(formula)}")
            }
        }.runWith(ctx)
    if (msg != "Bye!") {
        consoleRpnCalculator(ctx)
    }
}
