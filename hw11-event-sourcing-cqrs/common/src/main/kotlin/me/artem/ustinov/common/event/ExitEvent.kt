package me.artem.ustinov.common.event

import me.artem.ustinov.common.model.Direction
import me.artem.ustinov.common.model.Direction.EXIT
import java.time.LocalDateTime

data class ExitEvent(
        val uid: String,
        val exitTime: LocalDateTime,
        val direction: Direction = EXIT
)
