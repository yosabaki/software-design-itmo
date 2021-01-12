package servlet

import html.SEP
import html.wrapHtmlBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import ru.akirakozov.sd.refactoring.dao.ProductDao
import ru.akirakozov.sd.refactoring.entities.Product
import ru.akirakozov.sd.refactoring.servlet.AbstractProductServlet
import ru.akirakozov.sd.refactoring.servlet.QueryServlet
import java.io.PrintWriter
import java.io.StringWriter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.test.assertEquals

class QueryServletTest {
    @Mock
    private lateinit var productDao: ProductDao

    @Mock
    private lateinit var request: HttpServletRequest

    @Mock
    private lateinit var response: HttpServletResponse

    private lateinit var servlet: AbstractProductServlet

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        servlet = QueryServlet(productDao)
    }

    @Test
    fun maxTest() {
        `when`(request.getParameter("command"))
                .thenReturn("max")

        `when`(productDao.getMaxPriceProductOrNull())
                .thenReturn(Product("a", 1))

        val stringWriter = StringWriter()
        `when`(response.writer)
                .thenReturn(PrintWriter(stringWriter))

        servlet.doGet(request, response)
        assertEquals(
                wrapHtmlBody("<h1>Product with max price: </h1>${SEP}a\t1</br>$SEP"),
                stringWriter.toString()
        )
    }

    @Test
    fun minTest() {
        `when`(request.getParameter("command"))
                .thenReturn("min")

        `when`(productDao.getMinPriceProductOrNull())
                .thenReturn(Product("a", 1))

        val stringWriter = StringWriter()
        `when`(response.writer)
                .thenReturn(PrintWriter(stringWriter))

        servlet.doGet(request, response)
        assertEquals(
                wrapHtmlBody("<h1>Product with min price: </h1>${SEP}a\t1</br>$SEP"),
                stringWriter.toString()
        )
    }

    @Test
    fun sumTest() {
        `when`(request.getParameter("command"))
                .thenReturn("sum")

        `when`(productDao.getSum())
                .thenReturn(1L)

        val stringWriter = StringWriter()
        `when`(response.writer)
                .thenReturn(PrintWriter(stringWriter))

        servlet.doGet(request, response)

        assertEquals(
                wrapHtmlBody("<h1>Summary price: </h1>${SEP}1$SEP"),
                stringWriter.toString()
        )
    }

    @Test
    fun countTest() {
        `when`(request.getParameter("command"))
                .thenReturn("count")

        `when`(productDao.getCount())
                .thenReturn(1)

        val stringWriter = StringWriter()
        `when`(response.writer)
                .thenReturn(PrintWriter(stringWriter))

        servlet.doGet(request, response)

        assertEquals(
                wrapHtmlBody("<h1>Number of products: </h1>${SEP}1$SEP"),
                stringWriter.toString()
        )
    }
}