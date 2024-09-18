package com.taild.domain.usecase

import com.taild.domain.exception.BusinessException
import com.taild.domain.model.Product
import com.taild.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke() : Result<List<Product>> {
        return try {
            val listProduct = repository.getProducts()
            Result.success(listProduct)
        } catch (e: BusinessException) {
            Result.failure(e)
        }
    }
}