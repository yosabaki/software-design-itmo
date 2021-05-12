package me.artem.ustinov.client.repository

import me.artem.ustinov.client.domain.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface UserRepository : ReactiveCrudRepository<User, Long> {
    fun findByLogin(login: String): Mono<User>
}