package me.artem.ustinov.market.service

import me.artem.ustinov.common.dto.*
import me.artem.ustinov.common.exception.StockCompanyNotFoundException
import me.artem.ustinov.common.exception.StocksIllegalRequestException
import me.artem.ustinov.market.domain.Stocks
import me.artem.ustinov.market.dto.AddStocksRequestDto
import me.artem.ustinov.market.dto.UpdateStocksCountRequestDto
import me.artem.ustinov.market.dto.UpdateStocksPriceRequestDto
import me.artem.ustinov.market.mapper.StocksResponseMapper
import me.artem.ustinov.market.repository.StocksRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigDecimal

@Service
class MarketService(@Autowired private val stocksRepository: StocksRepository) {
    fun addStocks(dto: AddStocksRequestDto): Mono<StocksResponseDto> {
        return try {
            val savedStocks: Mono<Stocks> = stocksRepository.save(
                Stocks(dto.stocksName, dto.marketPlace, dto.count, dto.sellPrice)
            )
            savedStocks.map(StocksResponseMapper::mapToStocksResponse)
        } catch (e: Exception) {
            throw StocksIllegalRequestException(e.message, e)
        }
    }

    fun updateStocksCount(dto: UpdateStocksCountRequestDto): Mono<StocksResponseDto> {
        if (dto.count <= -1) {
            throw StocksIllegalRequestException("Stock count can't be negative")
        }
        return stocksRepository.findStocksByStocksName(dto.stocksName)
            .switchIfEmpty(
                Mono.error(
                    StockCompanyNotFoundException("Stock company ${dto.stocksName} not exist. Please check your request")
                )
            )
            .doOnNext { stocks ->
                stocksRepository.save(stocks.copy(count = stocks.count + dto.count)).subscribe()
            }
            .map(StocksResponseMapper::mapToStocksResponse)
    }

    fun updateStocksPrice(dto: UpdateStocksPriceRequestDto): Mono<StocksResponseDto> {
        if (dto.newPrice.signum() <= 0) {
            throw StocksIllegalRequestException("Stock price can't be zero or negative")
        }
        return stocksRepository.findStocksByStocksName(dto.stocksName)
            .switchIfEmpty(
                Mono.error(StockCompanyNotFoundException("Stock company ${dto.stocksName} not exist. Please check your request"))
            )
            .doOnNext { stocks ->
                stocksRepository.save(stocks.copy(sellPrice = dto.newPrice)).subscribe()
            }
            .map(StocksResponseMapper::mapToStocksResponse)
    }

    fun getAllStocks(): Flux<StocksResponseDto> = stocksRepository.findAll()
        .flatMap { stocks -> Mono.just(StocksResponseMapper.mapToStocksResponse(stocks)) }

    @Transactional
    fun buyStock(dto: BuyStockRequestDto): Mono<PaymentResponseDto> {
        return stocksRepository.findStocksByStocksName(dto.stocksName)
            .switchIfEmpty(
                Mono.error(
                    StockCompanyNotFoundException("Stock company ${dto.stocksName} not exist. Please check your request")
                )
            )
            .flatMap { stocks ->
                if (stocks.count < dto.count) {
                    return@flatMap Mono.error(
                        StocksIllegalRequestException("The number of shares for stock ${dto.stocksName} on the exchange is less [${dto.count}] than the number of shares [${stocks.count}] in the buy request")
                    )
                }
                if (stocks.sellPrice.subtract(dto.orderPrice).abs() > EPSILON_ERROR) {
                    return@flatMap Mono.error(
                        StocksIllegalRequestException("Stock price for ${dto.stocksName} has changed. Actual price is ${stocks.sellPrice}")
                    )
                }
                stocksRepository.save(stocks.copy(count = stocks.count - dto.count)).subscribe()
                Mono.just(stocks)
            }
            .map { stocks ->
                PaymentResponseDto(
                    stocks.id,
                    dto.stocksName,
                    dto.orderPrice,
                    stocks.sellPrice.multiply(BigDecimal(dto.count)),
                    dto.count
                )
            }
    }

    @Transactional
    fun sellStocks(dto: UserSellStockRequestDto): Mono<SellReportResponseDto> {
        return stocksRepository.findStocksByStocksName(dto.stocksName)
            .switchIfEmpty(
                Mono.error(
                    StockCompanyNotFoundException(
                        "Stock company ${dto.stocksName} not exist. Please check your request"
                    )
                )
            )
            .doOnNext { stocks ->
                if (stocks.sellPrice.subtract(dto.orderPrice).abs() < EPSILON_ERROR) {
                    throw StocksIllegalRequestException(
                        String.format(
                            "Your order price for ${dto.stocksName} is greater than actual price. Actual price is ${stocks.sellPrice}",
                        )
                    )
                }
                stocksRepository.save(stocks.copy(count = stocks.count + dto.count)).subscribe()
            }
            .map { stocks ->
                SellReportResponseDto(
                    stocks.id,
                    dto.login,
                    stocks.stocksName,
                    dto.orderPrice,
                    dto.count,
                )
            }
    }
}

private val EPSILON_ERROR = BigDecimal("1e-8")
