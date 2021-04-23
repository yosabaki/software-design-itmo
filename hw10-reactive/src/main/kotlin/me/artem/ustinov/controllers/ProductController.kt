package me.artem.ustinov.controllers

import me.artem.ustinov.model.Product
import me.artem.ustinov.services.ProductService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/products")
class ProductController(private val productService: ProductService) {

    @GetMapping("/{id}")
    fun getProduct(@PathVariable id: Long) : Mono<Product> = productService.getProduct(id)

    @PostMapping("/")
    fun createProduct(@RequestBody product: Product) = productService.createProduct(product)

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long) : Mono<Void> = productService.deleteProduct(id)
}