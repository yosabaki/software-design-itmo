package token

import visitor.TokenVisitor

enum class BraceType {
    LEFT, RIGHT
}

data class BraceToken(val type: BraceType) : Token {
    override fun accept(visitor: TokenVisitor) {
        TODO("Not yet implemented")
    }
}