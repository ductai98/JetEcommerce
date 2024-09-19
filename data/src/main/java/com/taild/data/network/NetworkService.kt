package com.taild.data.network

import com.taild.data.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkService {
    @GET("products")
    suspend fun getProducts(): List<ProductDto>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(
        @Path("category") category: String
    ): List<ProductDto>

    @GET("products/categories")
    suspend fun getCategories() : List<String>
}