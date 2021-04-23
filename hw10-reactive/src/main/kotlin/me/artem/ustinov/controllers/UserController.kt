package me.artem.ustinov.controllers

import me.artem.ustinov.model.Product
import me.artem.ustinov.model.User
import me.artem.ustinov.services.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody user: User): Mono<User> = userService.registerUser(user)

    @GetMapping("/")
    fun getAllUsers() : Flux<User> = userService.getAllUsers()

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long) : Mono<User> = userService.getUser(id)

    @GetMapping("/{id}/products")
    fun getProducts(@PathVariable id: Long) : Flux<Product> = userService.getProducts(id)
}