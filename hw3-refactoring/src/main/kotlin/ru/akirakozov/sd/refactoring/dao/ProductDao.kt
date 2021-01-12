package ru.akirakozov.sd.refactoring.dao

import ru.akirakozov.sd.refactoring.entities.Product

interface ProductDao {
    fun insert(product: Product)

    fun getProducts(): List<Product>

    fun getMaxPriceProductOrNull(): Product?

    fun getMinPriceProductOrNull(): Product?

    fun getSum(): Long

    fun getCount(): Int

    fun clear()
}