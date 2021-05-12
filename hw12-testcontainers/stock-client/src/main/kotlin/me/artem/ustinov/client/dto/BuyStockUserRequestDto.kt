package me.artem.ustinov.client.dto

import me.artem.ustinov.common.dto.BuyStockRequestDto

data class BuyStockUserRequestDto(
    val login: String,
    val id: Long,
    val request: BuyStockRequestDto
)