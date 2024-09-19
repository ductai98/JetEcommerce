package com.taild.data.dto

import com.taild.domain.model.Product
import kotlinx.serialization.Serializable

@Serializable
class ProductDto(
    val id: Long,
    val title: String,
    val price: Double,
    val category: String,
    val description: String,
    val image: String
) {
    val priceString: String
        get() = "$price"

    fun toProduct() = Product(
        id = id,
        title = title,
        price = price,
        image = image,
        category = category,
        description = description,
    )
}