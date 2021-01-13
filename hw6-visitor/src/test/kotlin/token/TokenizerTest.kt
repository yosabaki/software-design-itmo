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
                        NumberToken(1),
                        OperationToken(PLUS),
                        NumberToken(2),
                        OperationToken(MINUS),
                        NumberToken(3),
                        OperationToken(TIMES),
                        NumberToken(4),
                        OperationToken(DIVIDE),
                        NumberToken(5),
                ),
                tokenizer.tokenize("1+2-3*4/5")
        )
    }

    @Test
    fun braceTest() {
        assertEquals(
                listOf(
                        BraceToken(LEFT),
                        NumberToken(1),
                        BraceToken(RIGHT)
                ),
                tokenizer.tokenize("(1)")
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