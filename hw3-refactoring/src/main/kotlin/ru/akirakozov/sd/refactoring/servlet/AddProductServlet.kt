package ru.akirakozov.sd.refactoring.servlet

import ru.akirakozov.sd.refactoring.dao.ProductDao
import ru.akirakozov.sd.refactoring.dao.SQLProductDao
import ru.akirakozov.sd.refactoring.entities.Product
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author akirakozov
 */
class AddProductServlet(private val productDao: ProductDao) : HttpServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val name = request.getParameter("name")
        val price = request.getParameter("price").toLong()
        try {
            productDao.insert(Product(name, price))
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
        response.contentType = "text/html"
        response.status = HttpServletResponse.SC_OK
        response.writer.println("OK")
    }
}