package me.artem.ustinov.market.dto

import java.math.BigDecimal

data class UpdateStocksPriceRequestDto(
    val stocksName: String,
    val newPrice: BigDecimal
)