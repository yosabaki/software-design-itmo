package token

import visitor.TokenVisitor

enum class OperationType {
    PLUS, MINUS, TIMES, DIVIDE
}

data class OperationToken(val type: OperationType) : Token {
    override fun accept(visitor: TokenVisitor) {
        TODO("Not yet implemented")
    }
}