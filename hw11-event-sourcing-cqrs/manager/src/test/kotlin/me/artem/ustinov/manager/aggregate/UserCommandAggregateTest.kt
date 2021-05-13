package me.artem.ustinov.manager.aggregate

import me.artem.ustinov.common.event.SubscriptionUpdatedEvent
import me.artem.ustinov.common.event.UserCreatedEvent
import me.artem.ustinov.manager.command.CreateUserCommand
import me.artem.ustinov.manager.command.UpdateSubscriptionCommand
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.*

internal class UserCommandAggregateTest {
    private lateinit var fixture: FixtureConfiguration<UserCommandAggregate>

    @BeforeEach
    fun setUp() {
        fixture = AggregateTestFixture(UserCommandAggregate::class.java)
    }

    @Test
    fun testUseCreateCommand() {
        val userId = UUID.randomUUID().toString()
        val command = CreateUserCommand(userId);
        fixture.given(command, UserCreatedEvent(userId, command.createTime))
    }

    @Test
    fun testSubscriptionRenewedCommand() {
        val userId = UUID.randomUUID().toString()
        val command = UpdateSubscriptionCommand(userId, LocalDateTime.now())
        fixture.given(command, SubscriptionUpdatedEvent(userId, command.subscriptionUntil))
    }
}