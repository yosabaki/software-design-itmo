package me.artem.ustinov.turnstile.command

import me.artem.ustinov.common.model.Direction
import me.artem.ustinov.common.model.Direction.ENTER
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDateTime

data class EnterCommand(
    @TargetAggregateIdentifier val uid: String,
    val enterTime : LocalDateTime = LocalDateTime.now(),
    val direction: Direction = ENTER
)