package me.artem.ustinov.services

import me.artem.ustinov.dao.ProductRepository
import me.artem.ustinov.dao.UserRepository
import me.artem.ustinov.model.Product
import me.artem.ustinov.model.User
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UserService(private val userRepository: UserRepository, private val productRepository: ProductRepository) {

    fun registerUser(user: User): Mono<User> = userRepository.save(user)

    fun getAllUsers(): Flux<User> = userRepository.findAll()

    fun getUser(id: Long): Mono<User> = userRepository.findById(id)

    fun getProducts(id: Long): Flux<Product> =
        userRepository.findById(id).flatMapMany { user ->
            productRepository.findAll().map { product ->
                product.copy(price = product.currency.convert(product.price, user.currency))
            }
        }
}
