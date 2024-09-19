package com.taild.domain.usecase

import com.taild.domain.exception.BusinessException
import com.taild.domain.model.Product
import com.taild.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(category: String?) : Result<List<Product>> {
        return try {
            val listProduct = productRepository.getProducts(category)
            Result.success(listProduct)
        } catch (e: BusinessException) {
            Result.failure(e)
        }
    }
}