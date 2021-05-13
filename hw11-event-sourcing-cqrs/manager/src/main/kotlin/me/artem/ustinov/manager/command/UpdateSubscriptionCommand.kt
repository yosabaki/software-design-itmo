package me.artem.ustinov.manager.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDateTime

class UpdateSubscriptionCommand(
        @TargetAggregateIdentifier
        var uid: String,
        var subscriptionUntil: LocalDateTime
)
