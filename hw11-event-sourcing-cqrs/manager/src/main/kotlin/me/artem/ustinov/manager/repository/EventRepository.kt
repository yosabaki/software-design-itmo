package me.artem.ustinov.manager.repository

import me.artem.ustinov.manager.event.Event
import me.artem.ustinov.manager.event.EventId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EventRepository : JpaRepository<Event, EventId> {
    fun getFirstByUid(uid: String): Event?
}
