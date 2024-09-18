package com.taild.domain.repository

import com.taild.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
}