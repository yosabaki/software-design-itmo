package state

import token.*

class UnitState : State {
    override fun create(tokenizer: Tokenizer): Token {
        if (tokenizer.currentChar == ')') {
            tokenizer.nextChar()
            return BraceToken(BraceType.RIGHT)
        }
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
                    currentChar == ')' -> UnitState()
                    isOperation() -> OperationState()
                    else -> ErrorState("Unexpected symbol: $currentChar")
                }
            }
}