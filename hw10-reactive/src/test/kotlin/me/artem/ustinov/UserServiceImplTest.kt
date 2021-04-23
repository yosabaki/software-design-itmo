package me.artem.ustinov

import me.artem.ustinov.dao.ProductRepository
import me.artem.ustinov.dao.UserRepository
import me.artem.ustinov.model.Currency
import me.artem.ustinov.model.Product
import me.artem.ustinov.model.User
import me.artem.ustinov.services.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@ExtendWith(MockitoExtension::class)
internal class UserServiceImplTest {
    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var productRepository: ProductRepository

    @InjectMocks
    private lateinit var service: UserService

    @Test
    fun registerUserSuccess() {
        val expected = User(1, "", Currency.RUB)
        Mockito.`when`(userRepository.save(expected)).thenReturn(Mono.just(expected))
        val actual = service.registerUser(expected)
        Assertions.assertNotNull(actual)
        assertEquals(expected, actual.block())
        Mockito.verify(userRepository, Mockito.times(1)).save(expected)
    }

    @Test
    fun testGetAllUsers() {
        val expected = User(1, "", Currency.RUB)
        Mockito.`when`(userRepository.findAll()).thenReturn(Flux.just(expected))
        val actual: Flux<User> = service.getAllUsers()
        Assertions.assertNotNull(actual)
        assertEquals(expected, actual.blockFirst())
        Mockito.verify(userRepository, Mockito.times(1)).findAll()
    }

    @Test
    fun testGetUserError() {
        val id = 1L
        Mockito.`when`(userRepository.findById(id)).thenReturn(Mono.empty())
        val userById = service.getUser(id)
        assertNull(userById.block())
    }

    @Test
    fun testGetUserSuccess() {
        val expected = User(1, "", Currency.RUB)
        Mockito.`when`(userRepository.findById(expected.id)).thenReturn(Mono.just(expected))
        val actual = service.getUser(expected.id)
        Assertions.assertNotNull(actual)
        assertEquals(expected, actual.block())
        Mockito.verify(userRepository, Mockito.times(1)).findById(expected.id)
    }

    @Test
    fun testGetProducts() {
        val expected = User(1, "", Currency.EUR)
        val expectedProduct = Product(0, "", "", Currency.EUR.convert(1, Currency.RUB), Currency.RUB)
        Mockito.`when`(userRepository.findById(expected.id)).thenReturn(Mono.just(expected))
        Mockito.`when`(productRepository.findAll()).thenReturn(Flux.just(expectedProduct))
        val actual = service.getProducts(expected.id)
        val first = actual.blockFirst()
        Assertions.assertNotNull(actual)
        Assertions.assertNotNull(first)
        assertEquals(first!!.currency, expected.currency)
        assertEquals(first.price, 1)
    }
}