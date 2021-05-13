package me.artem.ustinov.report.repository

import me.artem.ustinov.common.model.Direction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import me.artem.ustinov.report.domain.EventId
import me.artem.ustinov.report.domain.GateEvent

interface TurnstileRepository : JpaRepository<GateEvent, EventId> {
    fun getFirstByEvent_UidAndDirectionOrderByEventTimeDesc(uid: String?, direction: Direction): GateEvent?

    @Modifying
    @Query(value = "SELECT Gate_Event.user_id, cast(count(*) AS int) AS exits_count" +
            " FROM Gate_Event " +
            " WHERE Gate_Event.direction = 'EXIT' " +
            "GROUP BY gate_event.user_id", nativeQuery = true)
    fun collectStat(): List<Array<Any?>>

    interface Stat {
        fun uid(): String?
        fun visits(): Int
    }
}