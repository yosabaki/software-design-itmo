package me.artem.ustinov.manager.event

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class UserStatus(
    @Id
    var uid: String = "",
    var creationDate: LocalDateTime = LocalDateTime.now(),
    var subscriptionUntil: LocalDateTime = LocalDateTime.now()
)