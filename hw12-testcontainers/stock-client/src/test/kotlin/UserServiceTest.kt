import me.artem.ustinov.client.domain.User
import me.artem.ustinov.client.dto.AddMoneyRequestDto
import me.artem.ustinov.client.dto.RegisterUserRequestDto
import me.artem.ustinov.common.dto.BaseResponse
import me.artem.ustinov.common.exception.StocksIllegalRequestException
import me.artem.ustinov.common.exception.UserAlreadyRegisteredException
import me.artem.ustinov.common.exception.UserNotFoundException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.LocalDateTime

internal class UserServiceTest: AbstractTestContainersIntegrationTest() {

    @Test
    fun testRegistration() {
        val dto = RegisterUserRequestDto("STALIN3000")
        val response = userController.registerUser(dto)
        Assertions.assertNotNull(response)
        assertEquals("STALIN3000", response.block()?.login)
    }

    @Test
    fun testRegistrationUserAlreadyExist() {
        val dto = RegisterUserRequestDto("STALIN3000")
        userController.registerUser(dto)
        val secondResponse = userController.registerUser(dto)
        Assertions.assertNotNull(secondResponse)
        Assertions.assertThrows(UserAlreadyRegisteredException::class.java) { secondResponse.block() }
    }

    @Test
    fun testAddMoneySuccess() {
        userRepository.save(User("STALIN3000", BigDecimal.ZERO, LocalDateTime.now())).subscribe()
        val dto = AddMoneyRequestDto("STALIN3000", BigDecimal(10))
        val response: Mono<BaseResponse> = userController.addMoneyToAccount(dto)
        Assertions.assertNotNull(response)
        assertTrue(response.block()!!.success)
        assertEquals(userRepository.findByLogin("STALIN3000").block()?.balance, BigDecimal(10))
    }

    @Test
    fun testAddMoneyUserNotExist() {
        val dto = AddMoneyRequestDto("STALIN3000", BigDecimal(10))
        val response: Mono<BaseResponse> = userController.addMoneyToAccount(dto)
        Assertions.assertNotNull(response)
        Assertions.assertThrows(UserNotFoundException::class.java) { response.block() }
    }

    @Test
    fun testAddMoneyUserIncorrectSum() {
        val dto = AddMoneyRequestDto("STALIN3000", BigDecimal(-1))
        Assertions.assertThrows(StocksIllegalRequestException::class.java) { userController.addMoneyToAccount(dto) }
    }
}