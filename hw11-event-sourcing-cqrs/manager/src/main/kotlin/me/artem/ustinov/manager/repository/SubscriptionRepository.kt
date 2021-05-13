package me.artem.ustinov.manager.repository

import me.artem.ustinov.manager.event.EventId
import me.artem.ustinov.manager.event.SubscriptionEvent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SubscriptionRepository : JpaRepository<SubscriptionEvent, EventId> {
    fun getFirstByEvent_UidOrderBySubscriptionUntilDesc(uid: String): SubscriptionEvent?
}
