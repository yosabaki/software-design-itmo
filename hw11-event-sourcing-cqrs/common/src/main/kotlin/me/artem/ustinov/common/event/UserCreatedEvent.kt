package me.artem.ustinov.common.event

import java.time.LocalDateTime

class UserCreatedEvent(val uid: String, val createTime: LocalDateTime)
