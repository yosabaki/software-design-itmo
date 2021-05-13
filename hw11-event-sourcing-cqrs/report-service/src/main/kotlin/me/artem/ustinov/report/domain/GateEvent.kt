package me.artem.ustinov.report.domain

import me.artem.ustinov.common.model.Direction
import java.time.LocalDateTime
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class GateEvent(
        @EmbeddedId
    private var event: EventId,
        var eventTime: LocalDateTime,
        @Enumerated(EnumType.STRING) var direction: Direction
)