package visitor

import org.junit.Before
import org.junit.Test
import token.*
import token.BraceType.LEFT
import token.BraceType.RIGHT
import token.OperationType.*
import java.text.ParseException
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFails

class ParserVisitorTest {
    private val parserVisitor = ParserVisitor()

    @Test
    fun numberTest() {
        assertEquals(
                listOf(NumberToken(1)),
                parserVisitor.parse(listOf(NumberToken(1)))
        )
    }

    // 1 + 2 - 4 * 3
    @Test
    fun simpleEquationTest() {
        assertEquals(
                listOf(
                        NumberToken(1),
                        NumberToken(2),
                        OperationToken(PLUS),
                        NumberToken(4),
                        NumberToken(3),
                        OperationToken(TIMES),
                        OperationToken(MINUS),
                ),
                parserVisitor.parse(
                        listOf(
                                NumberToken(1),
                                OperationToken(PLUS),
                                NumberToken(2),
                                OperationToken(MINUS),
                                NumberToken(4),
                                OperationToken(TIMES),
                                NumberToken(3),
                        )
                )
        )
    }

    // 1 + (2 + (3 + 4))
    @Test
    fun simpleEquationWithBracesTest() {
        assertEquals(
                listOf(
                        NumberToken(1),
                        NumberToken(2),
                        NumberToken(3),
                        NumberToken(4),
                        OperationToken(PLUS),
                        OperationToken(PLUS),
                        OperationToken(PLUS),
                ),
                parserVisitor.parse(
                        listOf(
                                NumberToken(1),
                                OperationToken(PLUS),
                                BraceToken(LEFT),
                                OperationToken(PLUS),
                                NumberToken(2),
                                BraceToken(LEFT),
                                NumberToken(3),
                                OperationToken(PLUS),
                                NumberToken(4),
                                BraceToken(RIGHT),
                                BraceToken(RIGHT)
                        )
                )
        )
    }

    // (1 + 2) * (3 + 4)
    @Test
    fun simpleEquationWithBraces2Test() {
        assertEquals(
                listOf(
                        NumberToken(1),
                        NumberToken(2),
                        OperationToken(PLUS),
                        NumberToken(3),
                        NumberToken(4),
                        OperationToken(PLUS),
                        OperationToken(TIMES),
                ),
                parserVisitor.parse(
                        listOf(
                                BraceToken(LEFT),
                                NumberToken(1),
                                OperationToken(PLUS),
                                NumberToken(2),
                                BraceToken(RIGHT),
                                OperationToken(TIMES),
                                BraceToken(LEFT),
                                NumberToken(3),
                                OperationToken(PLUS),
                                NumberToken(4),
                                BraceToken(RIGHT)
                        )
                )
        )
    }

    @Test
    fun missedBracesTest() {
        assertFails {
            parserVisitor.parse(
                    listOf(
                            BraceToken(LEFT),
                            BraceToken(LEFT),
                            BraceToken(RIGHT)
                    )
            )
        }
        assertFails {
            println(parserVisitor.parse(
                    listOf(
                            BraceToken(LEFT),
                            BraceToken(RIGHT),
                            BraceToken(RIGHT)
                    )
            ))
        }
    }
}