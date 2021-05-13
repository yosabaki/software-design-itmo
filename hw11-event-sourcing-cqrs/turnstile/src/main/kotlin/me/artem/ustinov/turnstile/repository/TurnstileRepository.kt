package me.artem.ustinov.turnstile.repository

import me.artem.ustinov.turnstile.model.EventId
import me.artem.ustinov.turnstile.model.GateEvent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TurnstileRepository : JpaRepository<GateEvent, EventId> {
    fun getFirstByEvent_UidOrderByEventTimeDesc(uid: String): GateEvent?
}