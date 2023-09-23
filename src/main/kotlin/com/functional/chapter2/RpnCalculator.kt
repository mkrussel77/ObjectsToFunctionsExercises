package com.functional.chapter2

fun calc(formula: String): Double = formula.split(" ").asSequence()
    .map { it.toToken() }
    .fold(FunStack<Operand>()) { stack, token -> stack.processToken(token)}
    .pop().first.number

private fun FunStack<Operand>.processToken(token: Token): FunStack<Operand> = when (token) {
    is Operand -> push(token)
    is Operator -> {
        val (operand2, stack1) = pop()
        val (operand1, stack2) = stack1.pop()
        stack2.push(token(operand1, operand2))
    }
}

private sealed interface Token

@JvmInline
private value class Operand(val number: Double) : Token

// the operators are not used by name but are used through a lookup
@Suppress("unused")
private enum class Operator(val symbol: String) : Token, (Operand, Operand) -> Operand {
    PLUS("+") {
        override fun invoke(p1: Operand, p2: Operand): Operand = Operand(p1.number + p2.number)
    },
    SUBTRACT("-") {
        override fun invoke(p1: Operand, p2: Operand): Operand = Operand(p1.number - p2.number)
    },
    DIVIDE("/") {
        override fun invoke(p1: Operand, p2: Operand): Operand = Operand(p1.number / p2.number)
    },
    MULTIPLY("*") {
        override fun invoke(p1: Operand, p2: Operand): Operand = Operand(p1.number * p2.number)
    }
}

private fun String.toToken() = this.toDoubleOrNull()?.let { Operand(it) } ?: this.toOperator()
private fun String.toOperator() = Operator.entries.first { it.symbol == this }
