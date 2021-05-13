package me.artem.ustinov.turnstile.repository

import me.artem.ustinov.turnstile.model.Event
import me.artem.ustinov.turnstile.model.EventId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EventRepository : JpaRepository<Event, EventId> {
    fun getFirstByUid(uid: String) : Event?
}
