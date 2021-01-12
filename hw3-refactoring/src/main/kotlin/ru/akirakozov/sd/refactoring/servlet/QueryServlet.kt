package ru.akirakozov.sd.refactoring.servlet

import ru.akirakozov.sd.refactoring.dao.ProductDao
import ru.akirakozov.sd.refactoring.dao.SQLProductDao
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author akirakozov
 */
class QueryServlet(private val productDao: ProductDao) : HttpServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val command = request.getParameter("command")
        if ("max" == command) {
            try {
                val product = productDao.getMaxPriceProductOrNull()
                response.writer.println("<html><body>")
                response.writer.println("<h1>Product with max price: </h1>")
                product?.let {
                    response.writer.println("${it.name}\t${it.price}")
                }
                response.writer.println("</body></html>")
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        } else if ("min" == command) {
            try {
                val product = productDao.getMinPriceProductOrNull()
                response.writer.println("<html><body>")
                response.writer.println("<h1>Product with min price: </h1>")
                product?.let {
                    response.writer.println("${it.name}\t${it.price}")
                }
                response.writer.println("</body></html>")
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        } else if ("sum" == command) {
            try {
                val sum = productDao.getSum()
                response.writer.println("<html><body>")
                response.writer.println("Summary price: ")
                response.writer.println(sum)
                response.writer.println("</body></html>")
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        } else if ("count" == command) {
            try {
                val cnt = productDao.getCount()
                response.writer.println("<html><body>")
                response.writer.println("Number of products: ")
                response.writer.println(cnt)
                response.writer.println("</body></html>")
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        } else {
            response.writer.println("Unknown command: $command")
        }
        response.contentType = "text/html"
        response.status = HttpServletResponse.SC_OK
    }
}