package token

import org.junit.Test
import kotlin.test.assertEquals
import token.OperationType.*
import token.BraceType.*
import java.rmi.UnexpectedException
import java.text.ParseException
import kotlin.test.assertFailsWith

class TokenizerTest {
    private val tokenizer = Tokenizer()

    @Test
    fun numberTest() {
        assertEquals(listOf(NumberToken(123)), tokenizer.tokenize("123"))
    }

    @Test
    fun operationTest() {
        assertEquals(
                listOf(
                        OperationToken(PLUS),
                        OperationToken(MINUS),
                        OperationToken(TIMES),
                        OperationToken(DIVIDE),
                ),
                tokenizer.tokenize("+-*/")
        )
    }

    @Test
    fun braceTest() {
        assertEquals(
                listOf(
                        BraceToken(LEFT),
                        BraceToken(RIGHT)
                ),
                tokenizer.tokenize("()")
        )
    }

    @Test
    fun whitespacesTest() {
        assertEquals(
                listOf(
                        NumberToken(123),
                        OperationToken(PLUS),
                        NumberToken(912)
                ),
                tokenizer.tokenize("     \n\r\t      123   \t\n\n     +  912   \r\t\n\n")
        )
    }

    @Test
    fun unexpectedSymbolTest() {
        assertFailsWith<ParseException> {
            tokenizer.tokenize("two plus two")
        }
    }


}