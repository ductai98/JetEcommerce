package com.taild.jetecommerce.navigation

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.taild.jetecommerce.model.UiProduct
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.Base64

val productNavType = object : NavType<UiProduct>(isNullableAllowed = true) {
    override fun get(bundle: Bundle, key: String): UiProduct? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, UiProduct::class.java)
        } else {
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): UiProduct {
        val item = Json.decodeFromString<UiProduct>(value)
        val decoded = item.copy(
            image = URLDecoder.decode(item.image, "utf-8"),
            description = String(Base64.getDecoder().decode(item.description.replace("_", "/"))),
            title = String(Base64.getDecoder().decode(item.title.replace("_", "/")))
        )
        return decoded
    }

    override fun serializeAsValue(value: UiProduct): String {
        val encoded = value.copy(
            image = URLEncoder.encode(value.image, "utf-8"),
            description = String(
                Base64.getEncoder().encode(value.description.toByteArray())
            ).replace("/", "_"),
            title = String(
                Base64.getEncoder().encode(value.title.toByteArray())
            ).replace("/", "_")
        )
        return Json.encodeToString(UiProduct.serializer(), encoded)
    }

    override fun put(bundle: Bundle, key: String, value: UiProduct) {
        bundle.putParcelable(key, value)
    }

}