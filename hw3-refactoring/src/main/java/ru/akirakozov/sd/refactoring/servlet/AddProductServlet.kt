package ru.akirakozov.sd.refactoring.servlet

import java.sql.DriverManager
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author akirakozov
 */
class AddProductServlet : HttpServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val name = request.getParameter("name")
        val price = request.getParameter("price").toLong()
        try {
            DriverManager.getConnection("jdbc:sqlite:test.db").use { c ->
                val sql = "INSERT INTO PRODUCT " +
                        "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")"
                val stmt = c.createStatement()
                stmt.executeUpdate(sql)
                stmt.close()
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
        response.contentType = "text/html"
        response.status = HttpServletResponse.SC_OK
        response.writer.println("OK")
    }
}