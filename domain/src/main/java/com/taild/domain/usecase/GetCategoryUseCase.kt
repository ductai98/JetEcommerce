package com.taild.domain.usecase

import com.taild.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke() : Result<List<String>> {
        return try {
            val listCategory = categoryRepository.getCategories()
            Result.success(listCategory)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}