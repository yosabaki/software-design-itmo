package state

import token.OperationType.*
import token.OperationToken
import token.Token
import token.Tokenizer
import java.text.ParseException

class OperationState : State {
    override fun create(tokenizer: Tokenizer): Token =
            when (tokenizer.nextChar()) {
                '+' -> OperationToken(PLUS)
                '-' -> OperationToken(MINUS)
                '*' -> OperationToken(TIMES)
                '/' -> OperationToken(DIVIDE)
                else -> throw ParseException("sd", 0)
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