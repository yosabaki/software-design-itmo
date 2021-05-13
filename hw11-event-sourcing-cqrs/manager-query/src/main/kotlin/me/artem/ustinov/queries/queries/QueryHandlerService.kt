package me.artem.ustinov.queries.queries

import me.artem.ustinov.common.event.SubscriptionUpdatedEvent
import me.artem.ustinov.common.event.UserCreatedEvent
import me.artem.ustinov.common.model.User
import org.axonframework.eventhandling.EventHandler
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.queryhandling.QueryHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap

@Service
class QueryHandlerService(
    @Autowired
    private val eventStore: EventStore
) {
    private val users: MutableMap<String, User> = ConcurrentHashMap<String, User>()
    private val subscriptions: MutableMap<String, LocalDateTime> = ConcurrentHashMap<String, LocalDateTime>()

    @EventHandler
    fun on(event: UserCreatedEvent) {
        val user = User(event.uid, event.createTime)
        users[event.uid] = user
    }

    @EventHandler
    fun on(event: SubscriptionUpdatedEvent) {
        subscriptions[event.uid] = event.subscriptionUntil
    }

    @QueryHandler
    fun handleGetUserQuery(query: GetUserQuery): User? {
        return users[query.uid]?.let {
            val userSubscriptionUntil: LocalDateTime? = subscriptions[it.uid]
            User(
                    it.uid,
                    it.created,
                    userSubscriptionUntil
            )
        }
    }
}