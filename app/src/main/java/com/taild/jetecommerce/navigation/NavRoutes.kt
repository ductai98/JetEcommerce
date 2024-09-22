package com.taild.jetecommerce.navigation

import com.taild.jetecommerce.model.UiProduct
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
object CartRoute

@Serializable
object ProfileRoute

@Serializable
data class DetailsRoute(val product: UiProduct)