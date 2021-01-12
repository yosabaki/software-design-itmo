package ru.akirakozov.sd.refactoring.servlet

import ru.akirakozov.sd.refactoring.dao.ProductDao
import ru.akirakozov.sd.refactoring.html.HTMLBodyWriter
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author akirakozov
 */
class QueryServlet(productDao: ProductDao) : AbstractProductServlet(productDao) {
    override fun doGetImpl(request: HttpServletRequest, response: HttpServletResponse) {
        HTMLBodyWriter(response.writer).use { body ->
            when (val command = request.getParameter("command")) {
                "max" -> {
                    val product = productDao.getMaxPriceProductOrNull()
                    body.addHeader("Product with max price: ")
                    product?.let {
                        body.addProduct(it)
                    }
                }
                "min" -> {
                    val product = productDao.getMinPriceProductOrNull()
                    body.addHeader("Product with min price: ")
                    product?.let {
                        body.addProduct(it)
                    }
                }
                "sum" -> {
                    val sum = productDao.getSum()
                    body.addHeader("Summary price: ")
                    body.add(sum)
                }
                "count" -> {
                    val cnt = productDao.getCount()
                    body.addHeader("Number of products: ")
                    body.add(cnt)
                }
                else -> {
                    body.addHeader("Unknown command: $command")
                }
            }
        }
    }
}