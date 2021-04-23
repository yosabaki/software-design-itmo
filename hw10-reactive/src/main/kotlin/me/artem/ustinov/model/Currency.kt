package me.artem.ustinov.model

enum class Currency {
    RUB, USD, EUR;

    private val multiplier: Int
        get() = when (this) {
            RUB -> 1
            USD -> 30
            EUR -> 40
        }

    fun convert(price: Int, other : Currency) = price / this.multiplier * other.multiplier
}