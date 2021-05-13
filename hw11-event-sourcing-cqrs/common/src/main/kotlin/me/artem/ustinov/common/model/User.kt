package me.artem.ustinov.common.model

import java.time.LocalDateTime


data class User(
        val uid: String,
        val created: LocalDateTime = LocalDateTime.now(),
        val subscriptionUntil: LocalDateTime? = null
)