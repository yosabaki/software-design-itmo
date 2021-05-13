package me.artem.ustinov.turnstile.command

import me.artem.ustinov.common.model.Direction
import me.artem.ustinov.common.model.Direction.EXIT
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDateTime

data class ExitCommand (
    @TargetAggregateIdentifier
    val uid: String,
    val exitTime : LocalDateTime = LocalDateTime.now(),
    val direction: Direction = EXIT
)
