package visitor

import token.BraceToken
import token.NumberToken
import token.OperationToken
import token.Token
import java.text.ParseException

class ParserVisitor : TokenVisitor {
    private val parsed = mutableListOf<Token>()
    private val stack = mutableListOf<Token>()

    fun parse(tokens: List<Token>): List<Token> {
        stack.clear()
        parsed.clear()
        tokens.forEach { it.accept(this) }
        stack.reversed().forEach {
            if (it is BraceToken) {
                throw ParseException("Missed right brace", 0)
            }
            parsed += it
        }
        return parsed
    }

    override fun visit(token: NumberToken) {
        parsed += token
    }

    override fun visit(token: BraceToken) {
        if (token.isLeft) {
            stack += token
        } else {
            while (stack.isNotEmpty() && stack.last() !is BraceToken) {
                parsed += stack.removeLast()
            }
            if (stack.isEmpty() || (stack.removeLast() as BraceToken).isRight) {
                throw ParseException("Missed left brace", 0)
            }
        }
    }

    override fun visit(token: OperationToken) {
        while (stack.isNotEmpty() && stack.last() is OperationToken && (stack.last() as OperationToken).priority <= token.priority) {
            parsed += stack.removeLast()
        }
        stack += token
    }
}