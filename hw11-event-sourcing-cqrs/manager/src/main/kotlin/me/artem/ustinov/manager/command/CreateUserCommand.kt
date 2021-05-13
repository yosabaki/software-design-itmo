package me.artem.ustinov.manager.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDateTime

class CreateUserCommand(
        @TargetAggregateIdentifier
        var uid: String,
        var createTime: LocalDateTime = LocalDateTime.now()
)