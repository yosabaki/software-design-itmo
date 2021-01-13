package token

import visitor.TokenVisitor
import token.OperationType.*

enum class OperationType {
    PLUS, MINUS, TIMES, DIVIDE
}

data class OperationToken(val type: OperationType) : Token {

    val priority: Int
        get() = when (type) {
            TIMES, DIVIDE -> 1
            PLUS, MINUS -> 2
        }

    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}