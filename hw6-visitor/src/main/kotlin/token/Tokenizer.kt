package token

import state.EndState
import state.ErrorState
import state.StartState
import state.State
import java.text.ParseException

class Tokenizer {
    private var currentIndex = 0
    private val tokens = mutableListOf<Token>()
    private lateinit var expression: CharSequence

    val currentChar
        get() = expression[currentIndex]

    fun nextChar(): Char = expression[currentIndex++]

    fun isEof() = currentIndex >= expression.length
    fun isWhiteSpace() = currentChar.isWhitespace()
    fun isNumber() = currentChar.isDigit()
    fun isOperation() = currentChar in "+-*/"
    fun isBrace() = currentChar in "()"

    fun State.isTerminal(): Boolean {
        return this is EndState || this is ErrorState
    }

    fun tokenize(expression: CharSequence): List<Token> {
        this.expression = expression
        tokens.clear()
        currentIndex = 0

        while (!isEof() && isWhiteSpace()) {
            nextChar()
        }

        var state: State = StartState().next(this)
        while (!state.isTerminal()) {
            tokens += state.create(this)
            while (!isEof() && isWhiteSpace()) {
                nextChar()
            }
            state = state.next(this)
        }

        if (state is ErrorState) {
            throw ParseException(state.message, currentIndex)
        }
        return tokens
    }
}