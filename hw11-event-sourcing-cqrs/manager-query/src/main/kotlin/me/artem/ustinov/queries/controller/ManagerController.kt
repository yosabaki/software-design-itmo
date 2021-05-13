package me.artem.ustinov.queries.controller

import me.artem.ustinov.common.model.User
import me.artem.ustinov.queries.queries.GetUserQuery
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/manager")
class ManagerController(
        @Autowired private val queryGateway: QueryGateway
) {
    @GetMapping("/user/{id}")
    fun getUser(@PathVariable("id") uid: Int): User {
        return queryGateway.query(GetUserQuery(uid),
                ResponseTypes.instanceOf(User::class.java)).join()
    }
}