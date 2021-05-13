package me.artem.ustinov.turnstile.repository

import me.artem.ustinov.turnstile.model.EventId
import me.artem.ustinov.turnstile.model.SubscriptionEvent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface SubscriptionRepository : JpaRepository<SubscriptionEvent, EventId> {
    fun getFirstByEvent_UidOrderBySubscriptionUntilDesc(uid: String): SubscriptionEvent?
}
