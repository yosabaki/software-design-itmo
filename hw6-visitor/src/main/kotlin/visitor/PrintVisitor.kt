package visitor

import token.*
import token.BraceType.*
import token.OperationType.*

class PrintVisitor : TokenVisitor {
    private lateinit var builder: StringBuilder

    fun print(tokens: List<Token>): String {
        builder = StringBuilder()
        tokens.forEach { it.accept(this) }

        return builder.toString()
    }

    override fun visit(token: NumberToken) {
        append(token.value)
    }

    override fun visit(token: BraceToken) {
        throw UnsupportedOperationException()
    }

    override fun visit(token: OperationToken) {
        val symbol = when (token.type) {
            PLUS -> '+'
            MINUS -> '-'
            TIMES -> '*'
            DIVIDE -> '/'
        }
        append(symbol)
    }

    private fun append(value: Any) {
        builder.append(value).append(' ')
    }
}