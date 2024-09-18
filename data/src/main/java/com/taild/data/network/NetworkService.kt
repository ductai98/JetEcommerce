package com.taild.data.network

import com.taild.data.entity.ProductEntity
import com.taild.domain.model.Product
import retrofit2.http.GET

interface NetworkService {
    @GET("products")
    suspend fun getProducts(): List<ProductEntity>
}