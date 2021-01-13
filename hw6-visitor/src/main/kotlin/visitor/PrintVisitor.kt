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
        builder.append(token.value)
    }

    override fun visit(token: BraceToken) {
        val char = when (token.type) {
            LEFT -> '('
            RIGHT -> ')'
        }
        builder.append(char)
    }

    override fun visit(token: OperationToken) {
        val sequence = when (token.type) {
            PLUS -> " + "
            MINUS -> " - "
            TIMES -> " * "
            DIVIDE -> " / "
        }
        builder.append(sequence)
    }
}