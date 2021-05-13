package me.artem.ustinov.turnstile.model

import me.artem.ustinov.common.model.Direction
import java.time.LocalDateTime
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class GateEvent(
    @EmbeddedId
    private var event: EventId = EventId(),
    var eventTime: LocalDateTime = LocalDateTime.now(),
    @Enumerated(EnumType.STRING) var direction: Direction = Direction.ENTER
)