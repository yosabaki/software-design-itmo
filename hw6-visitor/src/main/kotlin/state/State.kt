package state

import token.Token
import token.Tokenizer

interface State {
    fun create(tokenizer: Tokenizer) : Token

    fun next(tokenizer: Tokenizer) : State
}