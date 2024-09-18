package com.taild.data.network

import com.taild.data.entity.ProductEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkService {
    @GET("products")
    suspend fun getProducts(): List<ProductEntity>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(
        @Path("category") category: String
    ): List<ProductEntity>
}