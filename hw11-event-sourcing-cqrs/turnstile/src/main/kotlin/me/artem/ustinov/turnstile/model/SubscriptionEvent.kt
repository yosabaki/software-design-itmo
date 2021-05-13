package me.artem.ustinov.turnstile.model

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity(name = "SubscriptionEvents")
@Table(name = "subscriptionevents")
data class SubscriptionEvent(
    @EmbeddedId
    var event: EventId = EventId(),
    @Column(name = "end_date") var subscriptionUntil: LocalDateTime = LocalDateTime.now()
)