package servlet

import html.SEP
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import ru.akirakozov.sd.refactoring.dao.ProductDao
import ru.akirakozov.sd.refactoring.entities.Product
import ru.akirakozov.sd.refactoring.servlet.AbstractProductServlet
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet
import java.io.PrintWriter
import java.io.StringWriter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.test.assertEquals


class AddProductServletTest {
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
        servlet = AddProductServlet(productDao)
    }

    @Test
    fun getProductsTest() {
        `when`(request.getParameter("name")).thenReturn("a")
        `when`(request.getParameter("price")).thenReturn("1")

        val stringWriter = StringWriter()
        `when`(response.writer).thenReturn(PrintWriter(stringWriter))

        servlet.doGet(request, response)

        val captor = ArgumentCaptor.forClass(Product::class.java)
        verify(productDao, times(1)).insert(captor.capture())

        val captured = captor.value
        assertEquals(Product("a", 1L), captured)

        assertEquals(
                "OK$SEP",
                stringWriter.toString()
        )
    }
}