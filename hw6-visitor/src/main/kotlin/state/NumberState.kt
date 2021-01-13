package state

import token.NumberToken
import token.Token
import token.Tokenizer

class NumberState : State {
    override fun create(tokenizer: Tokenizer): Token {
        val sb = StringBuilder()
        while (!tokenizer.isEof() && tokenizer.isNumber()) {
            sb.append(tokenizer.nextChar())
        }
        return NumberToken(sb.toString().toInt())
    }

    override fun next(tokenizer: Tokenizer) : State =
            tokenizer.run {
                when {
                    isEof() -> EndState()
                    isNumber() -> NumberState()
                    isBrace() || isOperation() -> StartState()
                    else -> ErrorState("Unexpected symbol: $currentChar")
                }
            }
}