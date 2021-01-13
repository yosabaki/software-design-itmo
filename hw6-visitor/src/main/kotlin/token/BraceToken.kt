package token

import visitor.TokenVisitor

enum class BraceType {
    LEFT, RIGHT
}

data class BraceToken(val type: BraceType) : Token {
    val isLeft: Boolean
        get() = type == BraceType.LEFT

    val isRight: Boolean
        get() = type == BraceType.RIGHT

    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}