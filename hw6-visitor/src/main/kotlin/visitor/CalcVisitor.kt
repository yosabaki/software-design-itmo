package visitor

import token.*
import token.OperationType.*
import java.lang.IllegalStateException

class CalcVisitor : TokenVisitor {
    private val stack = mutableListOf<Int>()

    fun eval(tokens: List<Token>): Int {
        stack.clear()
        if (tokens.isEmpty()) {
            return 0
        }
        tokens.forEach { it.accept(this) }

        if (stack.size != 1) {
            throw IllegalStateException("Invalid expression")
        }

        return stack.last()
    }

    override fun visit(token: NumberToken) {
        stack += token.value
    }

    override fun visit(token: BraceToken) {
        throw IllegalStateException("Invalid expression")
    }

    override fun visit(token: OperationToken) {
        if (stack.size < 2) {
            throw IllegalStateException("Invalid expression")
        }
        val b = stack.removeLast()
        val a = stack.removeLast()
        stack += when (token.type) {
            PLUS -> a + b
            MINUS -> a - b
            TIMES -> a * b
            DIVIDE -> a / b
        }
    }
}