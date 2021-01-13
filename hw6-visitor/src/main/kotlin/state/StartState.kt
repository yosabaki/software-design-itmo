package state

import token.BraceToken
import token.BraceType.*
import token.OperationType.*
import token.OperationToken
import token.Token
import token.Tokenizer
import java.text.ParseException

class StartState : State {
    override fun create(tokenizer: Tokenizer): Token =
            when (tokenizer.nextChar()) {
                '(' -> BraceToken(LEFT)
                ')' -> BraceToken(RIGHT)
                '+' -> OperationToken(PLUS)
                '-' -> OperationToken(MINUS)
                '*' -> OperationToken(TIMES)
                '/' -> OperationToken(DIVIDE)
                else -> throw ParseException("sd", 0)
            }

    override fun next(tokenizer: Tokenizer): State =
            tokenizer.run {
                when {
                    isEof() -> EndState()
                    isNumber() -> NumberState()
                    isBrace() || isOperation() -> StartState()
                    else -> ErrorState("Unexpected symbol: $currentChar")
                }
            }
}