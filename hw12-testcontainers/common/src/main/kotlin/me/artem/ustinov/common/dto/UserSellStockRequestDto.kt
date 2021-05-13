package me.artem.ustinov.common.dto

import java.math.BigDecimal

data class UserSellStockRequestDto(
    val login: String,
    val stocksName: String,
    val orderPrice: BigDecimal,
    val count: Int = 0
)