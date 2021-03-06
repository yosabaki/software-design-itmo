package state

import token.BraceToken
import token.BraceType.*
import token.Token
import token.Tokenizer

class StartState : State {
    override fun create(tokenizer: Tokenizer): Token =
            when (tokenizer.nextChar()) {
                '(' -> BraceToken(LEFT)
                else -> error("")
            }

    override fun next(tokenizer: Tokenizer): State =
            tokenizer.run {
                when {
                    isEof() -> ErrorState("Unexpected end of string")
                    isNumber() -> UnitState()
                    currentChar == '(' -> StartState()
                    else -> ErrorState("Unexpected symbol: $currentChar")
                }
            }
}