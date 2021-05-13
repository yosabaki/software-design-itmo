package me.artem.ustinov.manager.query

import me.artem.ustinov.common.event.UserCreatedEvent
import me.artem.ustinov.manager.event.Event
import me.artem.ustinov.manager.event.EventId
import me.artem.ustinov.manager.event.SubscriptionEvent
import me.artem.ustinov.manager.repository.EventRepository
import me.artem.ustinov.manager.repository.SubscriptionRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class QueryHandlerServiceTest {
    @Mock
    private lateinit var subscriptionRepository: SubscriptionRepository

    @Mock
    private lateinit var eventRepository: EventRepository

    @InjectMocks
    private lateinit var queryHandlerService: QueryHandlerService

    @Test
    fun testEventHandler() {
        val uuid = UUID.randomUUID().toString()
        queryHandlerService.on(UserCreatedEvent(uuid, LocalDateTime.now()))
        val argumentCaptor = ArgumentCaptor.forClass(Event::class.java)
        Mockito.verify(eventRepository, Mockito.times(1)).save(argumentCaptor.capture())
        assertEquals(argumentCaptor.value.uid, uuid)
    }

    @Test
    fun testQueryHandler() {
        val uuid = UUID.randomUUID().toString()
        val sub = LocalDateTime.now()
        Mockito.`when`(eventRepository.getFirstByUid(uuid)).thenReturn(Event(uuid))
        Mockito.`when`(subscriptionRepository.getFirstByEvent_UidOrderBySubscriptionUntilDesc(uuid))
            .thenReturn(
                SubscriptionEvent(EventId(uuid, 1), sub)
            )
        val userById = queryHandlerService.getUserById(GetUserQuery(uuid))
        Assertions.assertNotNull(userById)
        assertEquals(uuid, userById.uid)
        assertEquals(sub, userById.subscriptionUntil)
    }

    @Test
    fun testQueryHandlerThrowsException() {
        val uuid: String = UUID.randomUUID().toString()
        Mockito.`when`<Event>(eventRepository.getFirstByUid(uuid)).thenReturn(null)
        Assertions.assertThrows(IllegalStateException::class.java) {
            queryHandlerService.getUserById(
                GetUserQuery(uuid)
            )
        }
    }
}