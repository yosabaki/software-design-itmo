package me.artem.ustinov.report.domain

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity(name = "SubscriptionEvents")
@Table(name = "subscription_events")
class SubscriptionEvent(
        @EmbeddedId
        val event: EventId,
        @Column(name="end_date") var subscriptionUntil: LocalDateTime
)
