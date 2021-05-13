package me.artem.ustinov.common.event

import java.time.LocalDateTime

data class SubscriptionUpdatedEvent(
    var uid: String,
    var subscriptionUntil: LocalDateTime
)