package me.artem.ustinov.turnstile.model

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class UserStatus(
    @Id
    var uid: String = "",
    var creationDate: LocalDateTime = LocalDateTime.now(),
    var subscriptionUntil: LocalDateTime = LocalDateTime.now()
)