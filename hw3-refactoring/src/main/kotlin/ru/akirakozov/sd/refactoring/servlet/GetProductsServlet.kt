package ru.akirakozov.sd.refactoring.servlet

import ru.akirakozov.sd.refactoring.dao.ProductDao
import ru.akirakozov.sd.refactoring.html.HTMLBodyWriter
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
            HTMLBodyWriter(response.writer).use { body ->
                products.forEach { body.addProduct(it) }
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
        response.contentType = "text/html"
        response.status = HttpServletResponse.SC_OK
    }
}