package state

import token.Token
import token.Tokenizer

class EndState : State {
    override fun create(tokenizer: Tokenizer): Token {
        throw UnsupportedOperationException()
    }

    override fun next(tokenizer: Tokenizer) : State {
        throw UnsupportedOperationException()
    }
}