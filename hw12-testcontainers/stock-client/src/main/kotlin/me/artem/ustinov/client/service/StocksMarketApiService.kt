package me.artem.ustinov.client.service

import me.artem.ustinov.client.ApiClient
import me.artem.ustinov.common.dto.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
@Transactional
class StocksMarketApiService(@Autowired private val apiClient: ApiClient) {

    fun buyStocks(request: BuyStockRequestDto): Mono<PaymentResponseDto> =
        apiClient.invokePostAPI("/api/v1/market/stocks/buy", request, PaymentResponseDto::class.java)

    fun sellStocks(request: UserSellStockRequestDto): Mono<SellReportResponseDto> =
        apiClient.invokePostAPI("/api/v1/market/stocks/sell", request, SellReportResponseDto::class.java)

    fun getAllStocks(): Flux<StocksResponseDto> =
        apiClient.invokeGetAPI("api/v1/market/stocks", null, null, null, StocksResponseDto::class.java)
}