package com.taild.jetecommerce.model

import android.os.Parcelable
import com.taild.domain.model.Product
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class UiProduct(
    val id: Long,
    val title: String,
    val price: Double,
    val category: String,
    val description: String,
    val image: String
) : Parcelable {
    companion object {
        fun fromProduct(product: Product) = UiProduct(
            id = product.id,
            title = product.title,
            price = product.price,
            category = product.category,
            description = product.description,
            image = product.image
        )
    }
}