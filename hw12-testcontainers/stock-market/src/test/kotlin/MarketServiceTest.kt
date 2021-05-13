import me.artem.ustinov.common.dto.BuyStockRequestDto
import me.artem.ustinov.common.dto.PaymentResponseDto
import me.artem.ustinov.common.dto.StocksResponseDto
import me.artem.ustinov.common.exception.StockCompanyNotFoundException
import me.artem.ustinov.common.exception.StocksIllegalRequestException
import me.artem.ustinov.market.domain.Stocks
import me.artem.ustinov.market.dto.AddStocksRequestDto
import me.artem.ustinov.market.dto.UpdateStocksCountRequestDto
import me.artem.ustinov.market.dto.UpdateStocksPriceRequestDto
import me.artem.ustinov.market.repository.StocksRepository
import me.artem.ustinov.market.service.MarketService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
internal class MarketServiceTest {
    @Mock
    lateinit var stocksRepository: StocksRepository

    @InjectMocks
    lateinit var service: MarketService

    @Test
    fun addStocksSuccess() {
        val dto = AddStocksRequestDto("SPCE Virgin", "СПБ Биржа", BigDecimal(36.3), 10)
        val expected = Stocks(dto.stocksName, dto.marketPlace, dto.count, dto.sellPrice)
        Mockito.`when`(stocksRepository.save(ArgumentMatchers.any())).thenReturn(Mono.just(expected))
        val actual = service.addStocks(dto)
        Assertions.assertNotNull(actual)
        assertEquals(expected.stocksName, actual.block()?.stocksName)
        Mockito.verify(stocksRepository, Mockito.times(1)).save(ArgumentMatchers.any())
    }

    @Test
    fun addStockThatAlreadyExist() {
        val dto = AddStocksRequestDto("SPCE Virgin", "СПБ Биржа", BigDecimal(36.3), 10)
        Mockito.`when`(stocksRepository.save(ArgumentMatchers.any())).thenThrow(RuntimeException())
        Assertions.assertThrows(StocksIllegalRequestException::class.java) { service.addStocks(dto) }
    }

    @Test
    fun updateStocksIllegalCount() {
        val dto = UpdateStocksCountRequestDto("SPCE Virgin", -1)
        Assertions.assertThrows(StocksIllegalRequestException::class.java) { service.updateStocksCount(dto) }
    }

    @Test
    fun updateStocksCountForNotExistingCompany() {
        val dto = UpdateStocksCountRequestDto("SPCE Virgin", 10)
        Mockito.`when`(stocksRepository.findStocksByStocksName(dto.stocksName)).thenReturn(Mono.empty())
        val response: Mono<StocksResponseDto> = service.updateStocksCount(dto)
        Assertions.assertThrows(StockCompanyNotFoundException::class.java) { response.block() }
    }

    @Test
    fun updateStocksIllegalPrice() {
        val dto = UpdateStocksPriceRequestDto("SPCE Virgin", BigDecimal(-1))
        Assertions.assertThrows(StocksIllegalRequestException::class.java) { service.updateStocksPrice(dto) }
    }

    @Test
    fun updateStocksPriceForNotExistingCompany() {
        val dto = UpdateStocksPriceRequestDto("SPCE Virgin", BigDecimal(10))
        Mockito.`when`(stocksRepository.findStocksByStocksName(dto.stocksName)).thenReturn(Mono.empty())
        val response: Mono<StocksResponseDto> = service.updateStocksPrice(dto)
        Assertions.assertThrows(StockCompanyNotFoundException::class.java) { response.block() }
    }

    @Test
    fun getAllStocks() {
        val expected = Stocks("SPCE Virgin", "СПБ Биржа", 10, BigDecimal(36))
        Mockito.`when`(stocksRepository.findAll()).thenReturn(Flux.just(expected))
        val allStocks: Flux<StocksResponseDto> = service.getAllStocks()
        Assertions.assertNotNull(allStocks)
        assertEquals(allStocks.blockFirst()?.stocksName, expected.stocksName)
    }

    @Test
    fun buyStockCompanyNotExist() {
        val dto = BuyStockRequestDto("SPCE Virgin", BigDecimal(36), 10)
        Mockito.`when`(stocksRepository.findStocksByStocksName(dto.stocksName)).thenReturn(Mono.empty())
        val response = service.buyStock(dto)
        Assertions.assertThrows(StockCompanyNotFoundException::class.java) { response.block() }
    }

    @Test
    fun buyStockCompanyAmbiguousCount() {
        val dto = BuyStockRequestDto("SPCE Virgin", BigDecimal(36), 1000000)
        val expected = Stocks("SPCE Virgin", "СПБ Биржа", 10, BigDecimal(36))
        Mockito.`when`(stocksRepository.findStocksByStocksName(dto.stocksName)).thenReturn(Mono.just(expected))
        val paymentResponseDtoMono: Mono<PaymentResponseDto> = service.buyStock(dto)
        Assertions.assertThrows(StocksIllegalRequestException::class.java) { paymentResponseDtoMono.block() }
    }

    @Test
    fun buyStockCompanyForIncorrectPrice() {
        val dto = BuyStockRequestDto("SPCE Virgin", BigDecimal(36), 10)
        val expected = Stocks("SPCE Virgin", "СПБ Биржа", 10, BigDecimal(35))
        Mockito.`when`(stocksRepository.findStocksByStocksName(dto.stocksName)).thenReturn(Mono.just(expected))
        val paymentResponseDtoMono: Mono<PaymentResponseDto> = service.buyStock(dto)
        Assertions.assertThrows(StocksIllegalRequestException::class.java) { paymentResponseDtoMono.block() }
    }
}