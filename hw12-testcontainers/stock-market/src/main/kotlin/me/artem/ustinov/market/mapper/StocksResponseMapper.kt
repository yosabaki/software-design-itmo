package me.artem.ustinov.market.mapper

import me.artem.ustinov.common.dto.StocksResponseDto
import me.artem.ustinov.market.domain.Stocks

object StocksResponseMapper {
    fun mapToStocksResponse(stocks: Stocks): StocksResponseDto {
        return StocksResponseDto(
            stocks.stocksName,
            stocks.marketPlace,
            stocks.sellPrice.toString(),
            stocks.count
        )
    }
}