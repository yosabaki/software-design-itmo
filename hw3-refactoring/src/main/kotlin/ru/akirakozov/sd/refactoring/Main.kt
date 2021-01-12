package ru.akirakozov.sd.refactoring

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import ru.akirakozov.sd.refactoring.dao.SQLProductDao
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet
import ru.akirakozov.sd.refactoring.servlet.QueryServlet

/**
 * @author akirakozov
 */
fun main() {
    val productDao = SQLProductDao()
    val server = Server(8081)
    val context = ServletContextHandler(ServletContextHandler.SESSIONS)
    context.contextPath = "/"
    server.handler = context
    context.addServlet(ServletHolder(AddProductServlet(productDao)), "/add-product")
    context.addServlet(ServletHolder(GetProductsServlet(productDao)), "/get-products")
    context.addServlet(ServletHolder(QueryServlet(productDao)), "/query")
    server.start()
    server.join()
}
