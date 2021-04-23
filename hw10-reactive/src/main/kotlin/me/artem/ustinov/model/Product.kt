package me.artem.ustinov.model

import org.springframework.data.annotation.Id

data class Product(
    @Id
    val id: Long,
    val name: String,
    val description: String,
    val price: Int,
    val currency: Currency,
)