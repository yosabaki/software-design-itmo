package me.artem.ustinov.turnstile.aggregate

import me.artem.ustinov.common.event.EnterEvent
import me.artem.ustinov.common.event.ExitEvent
import me.artem.ustinov.common.model.Direction.ENTER
import me.artem.ustinov.common.model.Direction.EXIT
import me.artem.ustinov.turnstile.command.EnterCommand
import me.artem.ustinov.turnstile.command.ExitCommand
import me.artem.ustinov.turnstile.model.Event
import me.artem.ustinov.turnstile.model.EventId
import me.artem.ustinov.turnstile.model.GateEvent
import me.artem.ustinov.turnstile.repository.EventRepository
import me.artem.ustinov.turnstile.repository.SubscriptionRepository
import me.artem.ustinov.turnstile.repository.TurnstileRepository
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Aggregate
class TurnstileCommandAggregate protected constructor() {
    @AggregateIdentifier
    private var uid: String = UUID.randomUUID().toString()

    @CommandHandler
    constructor(command: EnterCommand) : this() {
        val event = EnterEvent(command.uid, command.enterTime)
        AggregateLifecycle.apply(event)
    }

    @CommandHandler
    constructor(command: ExitCommand) : this() {
        val event = ExitEvent(command.uid, command.exitTime)
        AggregateLifecycle.apply(event)
    }

    @EventSourcingHandler
    @Transactional
    fun handle(
        event: ExitEvent,
        @Autowired eventRepository: EventRepository,
        @Autowired subscriptionRepository: SubscriptionRepository,
        @Autowired turnstileRepository: TurnstileRepository
    ) {
        eventRepository.getFirstByUid(event.uid) ?: error("User with id ${event.uid} doesn't exist.")
        val subscription = subscriptionRepository.getFirstByEvent_UidOrderBySubscriptionUntilDesc(event.uid)
                ?: error("Subscription for user ${event.uid} is not found.")
        if (subscription.subscriptionUntil.isBefore(LocalDateTime.now())) {
            error("Subscription for user ${event.uid} has expired.")
        }

        val lastGateUserEvent: GateEvent = turnstileRepository.getFirstByEvent_UidOrderByEventTimeDesc(event.uid) ?:
            error("Something went wrong")

        if (lastGateUserEvent.direction == EXIT) {
            error("Something went wrong")
        }

        val enterEvent: Event = eventRepository.save(Event(event.uid))
        turnstileRepository.save(GateEvent(EventId(enterEvent.uid, enterEvent.id), LocalDateTime.now(), EXIT))
    }

    @EventSourcingHandler
    @Transactional
    fun handle(
            event: EnterEvent,
            @Autowired eventRepository: EventRepository,
            @Autowired subscriptionRepository: SubscriptionRepository,
            @Autowired turnstileRepository: TurnstileRepository
    ) {
        eventRepository.getFirstByUid(event.uid) ?: error("User with id ${event.uid} doesn't exist.")
        val subscription = subscriptionRepository.getFirstByEvent_UidOrderBySubscriptionUntilDesc(event.uid)
                ?: error("Subscription for user ${event.uid} is not found.")
        if (subscription.subscriptionUntil.isBefore(LocalDateTime.now())) {
            error("Subscription for user ${event.uid} has expired.")
        }

        val lastGateUserEvent: GateEvent = turnstileRepository.getFirstByEvent_UidOrderByEventTimeDesc(event.uid) ?:
            error("Something went wrong")

        if (lastGateUserEvent.direction == ENTER) {
            error("Something went wrong")
        }

        val enterEvent: Event = eventRepository.save(Event(event.uid))
        turnstileRepository.save(GateEvent(EventId(enterEvent.uid, enterEvent.id), LocalDateTime.now(), ENTER))
    }
}