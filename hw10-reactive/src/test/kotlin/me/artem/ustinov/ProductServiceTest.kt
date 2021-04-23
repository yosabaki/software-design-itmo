package me.artem.ustinov

import me.artem.ustinov.dao.ProductRepository
import me.artem.ustinov.model.Currency
import me.artem.ustinov.model.Product
import me.artem.ustinov.services.ProductService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Mono

@ExtendWith(MockitoExtension::class)
internal class ProductServiceTest {
    @Mock
    lateinit var productRepository: ProductRepository

    @InjectMocks
    lateinit var service: ProductService

    @Test
    fun testCreateProduct() {
        val expected = Product(0, "", "", 0, Currency.RUB)
        Mockito.`when`(productRepository.save(expected)).thenReturn(Mono.just(expected))
        val actual = service.createProduct(expected)
        Assertions.assertNotNull(actual)
        assertEquals(expected, actual.block())
        Mockito.verify(productRepository, Mockito.times(1)).save(expected)
    }

    @Test
    fun productByIdError() {
        val id = 1L
        Mockito.`when`(productRepository.findById(id)).thenReturn(Mono.empty())
        val userById = service.getProduct(id)
        Assertions.assertNull(userById.block())
    }

    @Test
    fun testProductByIdSuccess() {
        val expected = Product(0, "", "", 0, Currency.RUB)
        Mockito.`when`(productRepository.findById(expected.id)).thenReturn(Mono.just(expected))
        val actual = service.getProduct(expected.id)
        Assertions.assertNotNull(actual)
        assertEquals(expected, actual.block())
        Mockito.verify(productRepository, Mockito.times(1)).findById(expected.id)
    }

    @Test
    fun testDeleteProduct() {
        val id = 1L
        Mockito.`when`(productRepository.deleteById(id)).thenReturn(Mono.empty())
        val actual = service.deleteProduct(id)
        Assertions.assertNotNull(actual)
    }
}