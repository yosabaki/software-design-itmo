package ru.akirakozov.sd.refactoring.html

import ru.akirakozov.sd.refactoring.entities.Product
import java.io.PrintWriter
import javax.servlet.http.HttpServletResponse

class HTMLBodyWriter(private val writer: PrintWriter) : AutoCloseable {
    init {
        writer.println("<html><body>")
    }

    fun addHeader(header: String) {
        writer.println("<h1>$header</h1>")
    }

    fun add(value: Any) {
        writer.println("$value")
    }

    fun addProduct(product: Product) {
        writer.println("${product.name}\t${product.price}</br>")
    }

    override fun close() {
        writer.println("</body></html>")
    }
}