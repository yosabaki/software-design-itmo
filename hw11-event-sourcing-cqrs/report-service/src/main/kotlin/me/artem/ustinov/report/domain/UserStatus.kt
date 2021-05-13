package me.artem.ustinov.report.domain

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class UserStatus(
        @Id
        var uid: String,
        var creationDate: LocalDateTime,
        var subscriptionUntil: LocalDateTime
)