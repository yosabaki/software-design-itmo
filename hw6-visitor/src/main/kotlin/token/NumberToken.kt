package token

import visitor.TokenVisitor

data class NumberToken(val value: Int) : Token {
    override fun accept(visitor: TokenVisitor) {
        TODO("Not yet implemented")
    }
}