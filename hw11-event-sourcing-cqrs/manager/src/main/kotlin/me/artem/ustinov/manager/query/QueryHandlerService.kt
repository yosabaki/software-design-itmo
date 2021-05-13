package me.artem.ustinov.manager.query

import me.artem.ustinov.common.event.SubscriptionUpdatedEvent
import me.artem.ustinov.common.event.UserCreatedEvent
import me.artem.ustinov.common.model.User
import me.artem.ustinov.manager.event.Event
import me.artem.ustinov.manager.event.EventId
import me.artem.ustinov.manager.event.SubscriptionEvent
import me.artem.ustinov.manager.repository.EventRepository
import me.artem.ustinov.manager.repository.SubscriptionRepository
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class QueryHandlerService(
    @Autowired
    private val eventRepository: EventRepository,
    @Autowired
    private val subscriptionRepository: SubscriptionRepository
) {
    @EventHandler
    fun on(event: UserCreatedEvent) {
        eventRepository.save(Event(event.uid))
    }

    @EventHandler
    @Transactional
    fun on(event: SubscriptionUpdatedEvent) {
        eventRepository.getFirstByUid(event.uid)
            ?: error("User with provided id ${event.uid} not exist")
        val savedEvent = eventRepository.save(Event(event.uid))
        subscriptionRepository.save(
            SubscriptionEvent(
                EventId(event.uid, savedEvent.id),
                event.subscriptionUntil
            )
        )
    }

    @QueryHandler(queryName = "getUser")
    fun getUserById(query: GetUserQuery): User {
        eventRepository.getFirstByUid(query.uid) ?: error("User with provided id ${query.uid} not exist")
        val subscription = subscriptionRepository.getFirstByEvent_UidOrderBySubscriptionUntilDesc(query.uid)
        return User(query.uid, subscriptionUntil = subscription?.subscriptionUntil)
    }
}