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
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet
import java.io.PrintWriter
import java.io.StringWriter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.test.assertEquals

class GetProductsServletTest {
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
        servlet = GetProductsServlet(productDao)
    }

    @Test
    fun getProductsTest() {
        val products = listOf(Product("a", 1L))
        `when`(productDao.getProducts())
                .thenReturn(products)

        val stringWriter = StringWriter()
        `when`(response.writer)
                .thenReturn(PrintWriter(stringWriter))

        servlet.doGet(request, response)
        assertEquals(
                wrapHtmlBody("a\t1</br>$SEP"),
                stringWriter.toString()
        )
    }
}