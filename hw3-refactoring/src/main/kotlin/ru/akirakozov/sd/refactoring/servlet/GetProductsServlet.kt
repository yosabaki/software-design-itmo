package ru.akirakozov.sd.refactoring.servlet

import ru.akirakozov.sd.refactoring.dao.ProductDao
import ru.akirakozov.sd.refactoring.dao.SQLProductDao
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author akirakozov
 */
class GetProductsServlet(private val productDao: ProductDao) : HttpServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        try {
            val products = productDao.getProducts()
            response.writer.println("<html><body>")
            products.forEach { response.writer.println("${it.name}\t${it.price}</br>") }
            response.writer.println("</body></html>")
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
        response.contentType = "text/html"
        response.status = HttpServletResponse.SC_OK
    }
}