package me.artem.ustinov.common.model

import java.time.Duration

data class UserStat(
    var durationInMinutes: Duration,
    var visits: Int
)