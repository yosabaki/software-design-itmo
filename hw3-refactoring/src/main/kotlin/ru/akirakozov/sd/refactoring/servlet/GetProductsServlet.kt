package ru.akirakozov.sd.refactoring.servlet

import ru.akirakozov.sd.refactoring.dao.ProductDao
import ru.akirakozov.sd.refactoring.html.HTMLBodyWriter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author akirakozov
 */
class GetProductsServlet(productDao: ProductDao) : AbstractProductServlet(productDao) {
    override fun doGetImpl(request: HttpServletRequest, response: HttpServletResponse) {
        val products = productDao.getProducts()
        HTMLBodyWriter(response.writer).use { body ->
            products.forEach { body.addProduct(it) }
        }
    }
}