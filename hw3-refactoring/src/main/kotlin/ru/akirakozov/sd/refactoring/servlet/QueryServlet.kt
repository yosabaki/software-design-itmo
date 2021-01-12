package ru.akirakozov.sd.refactoring.servlet

import ru.akirakozov.sd.refactoring.dao.ProductDao
import ru.akirakozov.sd.refactoring.html.HTMLBodyWriter
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
                HTMLBodyWriter(response.writer).use { body ->
                    body.addHeader("Product with max price: ")
                    product?.let {
                        body.addProduct(it)
                    }
                }
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        } else if ("min" == command) {
            try {
                val product = productDao.getMinPriceProductOrNull()
                HTMLBodyWriter(response.writer).use { body ->
                    body.addHeader("Product with min price: ")
                    product?.let {
                        body.addProduct(it)
                    }
                }
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        } else if ("sum" == command) {
            try {
                val sum = productDao.getSum()
                HTMLBodyWriter(response.writer).use { body ->
                    body.addHeader("Summary price: ")
                    body.add(sum)
                }
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        } else if ("count" == command) {
            try {
                val cnt = productDao.getCount()
                HTMLBodyWriter(response.writer).use { body ->
                    body.addHeader("Number of products: ")
                    body.add(cnt)
                }
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