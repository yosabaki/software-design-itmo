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
class AddProductServlet(productDao: ProductDao) : AbstractProductServlet(productDao) {
    override fun doGetImpl(request: HttpServletRequest, response: HttpServletResponse) {
        val name = request.getParameter("name")
        val price = request.getParameter("price").toLong()
        productDao.insert(Product(name, price))
        response.writer.println("OK")
    }
}