package me.artem.ustinov.manager.event

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity(name = "SubscriptionEvents")
@Table(name = "subscriptionevents")
class SubscriptionEvent(
        @EmbeddedId
        val event: EventId,
        @Column(name="end_date") var subscriptionUntil: LocalDateTime
)
