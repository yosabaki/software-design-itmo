package ru.akirakozov.sd.refactoring.servlet

import ru.akirakozov.sd.refactoring.dao.ProductDao
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

abstract class AbstractProductServlet(protected val productDao: ProductDao) : HttpServlet() {

    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        try {
            doGetImpl(request, response)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

        response.contentType = "text/html"
        response.status = HttpServletResponse.SC_OK
    }

    protected abstract fun doGetImpl(request: HttpServletRequest, response: HttpServletResponse)

}