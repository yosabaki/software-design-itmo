package me.artem.ustinov.common.event

import me.artem.ustinov.common.model.Direction
import me.artem.ustinov.common.model.Direction.ENTER
import java.time.LocalDateTime

data class EnterEvent(
        val uid: String,
        val enterTime: LocalDateTime,
        val direction: Direction = ENTER
)
