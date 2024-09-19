package com.taild.domain.repository

import com.taild.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(category: String?): List<Product>
}

interface CategoryRepository {
    suspend fun getCategories(): List<String>
}