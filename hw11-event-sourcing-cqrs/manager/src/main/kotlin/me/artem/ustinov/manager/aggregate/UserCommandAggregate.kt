package me.artem.ustinov.manager.aggregate

import me.artem.ustinov.common.event.SubscriptionUpdatedEvent
import me.artem.ustinov.common.event.UserCreatedEvent
import me.artem.ustinov.manager.command.CreateUserCommand
import me.artem.ustinov.manager.command.UpdateSubscriptionCommand
import me.artem.ustinov.manager.event.Event
import me.artem.ustinov.manager.event.EventId
import me.artem.ustinov.manager.event.SubscriptionEvent
import me.artem.ustinov.manager.repository.EventRepository
import me.artem.ustinov.manager.repository.SubscriptionRepository
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Aggregate
@Service
class UserCommandAggregate protected constructor() {
    @AggregateIdentifier
    private val uid: String = UUID.randomUUID().toString()

    @CommandHandler
    constructor(command: CreateUserCommand) : this() {
        val event = UserCreatedEvent(command.uid, command.createTime)
        AggregateLifecycle.apply(event)
    }

    @CommandHandler
    constructor(command: UpdateSubscriptionCommand) : this() {
        val event = SubscriptionUpdatedEvent(command.uid, command.subscriptionUntil)
        AggregateLifecycle.apply(event)
    }

    @EventSourcingHandler
    fun handle(
        event: UserCreatedEvent,
        @Autowired @Lazy eventRepository: EventRepository
    ) {
        eventRepository.save(Event(event.uid))
    }

    @EventSourcingHandler
    @Transactional
    fun handle(
        event: SubscriptionUpdatedEvent,
        @Autowired @Lazy eventRepository: EventRepository,
        @Autowired @Lazy subscriptionRepository: SubscriptionRepository
    ) {
        eventRepository.getFirstByUid(event.uid) ?: error("User with provided id %s not exist. Something went wrong")
        val savedEvent: Event = eventRepository.save(Event(event.uid))
        subscriptionRepository.save(SubscriptionEvent(EventId(event.uid, savedEvent.id), event.subscriptionUntil))
    }
}