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

class CalcVisitorTest {
    private val calcVisitor = CalcVisitor()

    @Test
    fun numberTest() {
        assertEquals(
                1,
                calcVisitor.eval(listOf(NumberToken(1)))
        )
    }

    // 1 + 2 - 4 * 3
    @Test
    fun simpleEquationTest() {
        assertEquals(
                -9,
                calcVisitor.eval(
                        listOf(
                                NumberToken(1),
                                NumberToken(2),
                                OperationToken(PLUS),
                                NumberToken(4),
                                NumberToken(3),
                                OperationToken(TIMES),
                                OperationToken(MINUS),
                        )
                )
        )
    }

    // 1 + (2 - (3 * (4 / 5))))
    @Test
    fun simpleEquationWithBracesTest() {
        assertEquals(
                3,
                calcVisitor.eval(
                        listOf(
                                NumberToken(1),
                                NumberToken(2),
                                NumberToken(3),
                                NumberToken(4),
                                NumberToken(5),
                                OperationToken(DIVIDE),
                                OperationToken(TIMES),
                                OperationToken(MINUS),
                                OperationToken(PLUS),
                        )
                )
        )
    }
}