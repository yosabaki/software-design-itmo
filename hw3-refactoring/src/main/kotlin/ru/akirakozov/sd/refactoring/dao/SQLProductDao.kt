package ru.akirakozov.sd.refactoring.dao

import ru.akirakozov.sd.refactoring.entities.Product
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

private const val SQL_URL = "jdbc:sqlite:test.db"

class SQLProductDao(private val url: String = SQL_URL) : ProductDao {

    private val connection: Connection
        get() = DriverManager.getConnection(url)

    init {
        connection.createStatement().use { stmt ->
            val sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)"
            stmt.executeUpdate(sql)
        }
    }

    override fun insert(product: Product) {
        connection.createStatement().use { stmt ->
            val sql = "INSERT INTO PRODUCT " +
                    "(NAME, PRICE) VALUES (\"" + product.name + "\"," + product.price + ")"
            stmt.executeUpdate(sql)
        }
    }


    override fun getProducts(): List<Product> =
            connection.createStatement().use { stmt ->
                val sql = "SELECT * FROM PRODUCT"
                stmt.executeQuery(sql).parseProducts()
            }

    override fun getMaxPriceProductOrNull(): Product? =
            connection.createStatement().use { stmt ->
                val sql = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1"
                stmt.executeQuery(sql).parseProducts().firstOrNull()
            }
    override fun getMinPriceProductOrNull(): Product? =
            connection.createStatement().use { stmt ->
                val sql = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1"
                stmt.executeQuery(sql).parseProducts().firstOrNull()
            }

    override fun getSum(): Long =
            connection.createStatement().use { stmt ->
                val sql = "SELECT SUM(price) as sum FROM PRODUCT"
                stmt.executeQuery(sql).getLong("sum")
            }


    override fun getCount(): Int =
            connection.createStatement().use { stmt ->
                val sql = "SELECT COUNT(*) as cnt FROM PRODUCT"
                stmt.executeQuery(sql).getInt("cnt")
            }

    private fun ResultSet.parseProducts(): List<Product> =
            mutableListOf<Product>().also { list ->
                use { rs ->
                    while (rs.next()) {
                        val name = rs.getString("name")
                        val price = rs.getLong("price")
                        list += Product(name, price)
                    }
                }
            }

    override fun clear() {
        connection.createStatement().use { stmt ->
            val sql = "DELETE FROM PRODUCT"
            stmt.executeUpdate(sql)
        }
    }
}
