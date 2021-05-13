package me.artem.ustinov.manager.controller

import me.artem.ustinov.common.dto.CreateUserDto
import me.artem.ustinov.manager.command.UpdateSubscriptionCommand
import me.artem.ustinov.manager.command.CreateUserCommand
import me.artem.ustinov.manager.query.GetUserQuery
import org.apache.catalina.User
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.queryhandling.QueryGateway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping("/manager")
class ManagerController(
        @Autowired
        private val commandGateway: CommandGateway,
        @Autowired
        private val queryGateway: QueryGateway
) {

    @PostMapping("/user/new")
    fun register(@RequestBody dto: CreateUserDto): String {
        return commandGateway.send<Any>(CreateUserCommand(UUID.randomUUID().toString())).toString()
    }

    @PostMapping("/user/update")
    fun updateSubscription(@RequestBody command: UpdateSubscriptionCommand): ResponseEntity<String> {
        return try {
            val result = commandGateway.sendAndWait<Any>(command, 3, TimeUnit.SECONDS)
            ResponseEntity.ok(result.toString())
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
        }
    }

    @GetMapping("/user/{id}")
    fun getUser(@PathVariable("id") uid: String): CompletableFuture<User> {
        return queryGateway.query("getUser", GetUserQuery(uid), User::class.java)
    }
}