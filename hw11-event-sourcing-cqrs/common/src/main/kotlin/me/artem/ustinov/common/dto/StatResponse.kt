package me.artem.ustinov.common.dto

import java.time.Duration

data class StatResponse(
        val uid: String,
        val totalMinutes: Duration,
        val visitNumber: Int
)