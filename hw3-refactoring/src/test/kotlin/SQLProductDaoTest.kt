import org.junit.After
import org.junit.Test
import ru.akirakozov.sd.refactoring.dao.SQLProductDao
import ru.akirakozov.sd.refactoring.entities.Product
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SQLProductDaoTest {

    private val productDao = SQLProductDao()
    private val products = listOf(
            Product("A", 1),
            Product("B", 2),
            Product("C", 3)
    )

    @After
    fun clear() {
        productDao.clear()
    }

    @Test
    fun emptyTest() {
        assertEquals(0, productDao.getCount())
        assertEquals(0L, productDao.getSum())
        assert(productDao.getProducts().isEmpty())
        assertNull(productDao.getMaxPriceProductOrNull())
        assertNull(productDao.getMinPriceProductOrNull())
    }

    @Test
    fun singleInsertTest() {
        productDao.insert(products.first())
        assertEquals(1, productDao.getCount())
        assertEquals(1L, productDao.getSum())
        assertEquals(products.first(), productDao.getProducts().first())
    }

    @Test
    fun countTest() {
        products.forEachIndexed { i, product ->
            productDao.insert(product)
            assertEquals(i + 1, productDao.getCount())
        }
    }

    @Test
    fun sumTest() {
        products.fold(0L) { sum, product ->
            val newSum = sum + product.price
            productDao.insert(product)
            assertEquals(newSum, productDao.getSum())
            newSum
        }
    }

    @Test
    fun getProductsTest() {
        products.forEach { productDao.insert(it) }
        assertEquals(products, productDao.getProducts())
    }

    @Test
    fun minTest() {
        products.forEach { productDao.insert(it) }
        assertEquals(products.minByOrNull { it.price }, productDao.getMinPriceProductOrNull())
    }


    @Test
    fun maxTest() {
        products.forEach { productDao.insert(it) }
        assertEquals(products.maxByOrNull { it.price }, productDao.getMaxPriceProductOrNull())
    }
}