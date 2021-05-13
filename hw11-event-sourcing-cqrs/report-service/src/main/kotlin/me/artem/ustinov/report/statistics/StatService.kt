package me.artem.ustinov.report.statistics

import me.artem.ustinov.common.dto.StatResponse
import me.artem.ustinov.common.event.ExitEvent
import me.artem.ustinov.common.model.Direction.ENTER
import me.artem.ustinov.common.model.UserStat
import org.axonframework.eventhandling.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import me.artem.ustinov.report.domain.GateEvent
import me.artem.ustinov.report.repository.TurnstileRepository
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap
import javax.annotation.PostConstruct

@Service
class StatService(
        @Autowired private val turnstileRepository: TurnstileRepository
) {
    private val statMap: MutableMap<String, UserStat> = ConcurrentHashMap<String, UserStat>()

    @EventHandler
    fun on(exitEvent: ExitEvent) {
        val enterEvent: GateEvent = turnstileRepository.getFirstByEvent_UidAndDirectionOrderByEventTimeDesc(exitEvent.uid, ENTER)
                ?: error("something went wrong")
        val visitDuration = Duration.between(enterEvent.eventTime, exitEvent.exitTime)
        statMap.compute(exitEvent.uid) { _, userStat ->
            if (userStat == null) {
                UserStat(visitDuration, 1)
            } else {
                val newDuration: Duration = userStat.durationInMinutes.plus(visitDuration)
                val newVisits: Int = userStat.visits + 1
                UserStat(newDuration, newVisits)
            }
        }

    }

    @PostConstruct
    fun init() {
        val resultList: List<Array<Any?>> = turnstileRepository.collectStat()
        for (userStat in resultList) {
            val uid = userStat[0] as String
            val visits = userStat[1] as Int
            statMap[uid] = UserStat(Duration.ofMillis(100), visits)
        }
    }

    fun getStatForUserById(uid: String): StatResponse =
            statMap[uid]?.let {
                StatResponse(uid, it.durationInMinutes, it.visits)
            } ?: StatResponse(uid, Duration.ofMinutes(0), 0)
}