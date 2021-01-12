package html

import org.junit.After
import org.junit.Test
import ru.akirakozov.sd.refactoring.entities.Product
import ru.akirakozov.sd.refactoring.html.HTMLBodyWriter
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.test.assertEquals


class HTMLBodyWriterTest {
    private val stringWriter = StringWriter()
    private val htmlWriter: HTMLBodyWriter
        get() = HTMLBodyWriter(PrintWriter(stringWriter))

    @After
    fun clear() {
        stringWriter.flush()
    }

    @Test
    fun emptyTest() {
        htmlWriter.use { }
        assertEquals(wrapHtmlBody(""), stringWriter.toString())
    }

    @Test
    fun addLineTest() {
        htmlWriter.use { it.add("aaa") }
        assertEquals(wrapHtmlBody("aaa$SEP"), stringWriter.toString())
    }


    @Test
    fun addHeaderTest() {
        htmlWriter.use { it.addHeader("aaa") }

        val line = "<h1>aaa</h1>$SEP"
        assertEquals(wrapHtmlBody(line), stringWriter.toString())
    }


    @Test
    fun addProductTest() {
        val product = Product("aaa", 10L)
        htmlWriter.use { it.addProduct(product) }

        val line = "${product.name}\t${product.price}</br>$SEP"
        assertEquals(wrapHtmlBody(line), stringWriter.toString())
    }

}