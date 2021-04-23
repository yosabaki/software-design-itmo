package me.artem.ustinov.services

import me.artem.ustinov.dao.ProductRepository
import me.artem.ustinov.model.Product
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ProductService(private val productRepository: ProductRepository) {
    fun getProduct(id: Long): Mono<Product> = productRepository.findById(id)

    fun createProduct(product: Product): Mono<Product> = productRepository.save(product)

    fun deleteProduct(id: Long): Mono<Void?> = productRepository.deleteById(id)

    fun getAllProducts(): Flux<Product> = productRepository.findAll()
}
