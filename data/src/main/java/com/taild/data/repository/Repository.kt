package com.taild.data.repository

import com.taild.data.network.NetworkService
import com.taild.domain.exception.BusinessException
import com.taild.domain.model.Product
import com.taild.domain.repository.ProductRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class ProductRepositoryImpl @Inject constructor(
    private val service: NetworkService
) : ProductRepository {
    override suspend fun getProducts(category: String?): List<Product> {
        try {
            if (category.isNullOrBlank()) {
                return service.getProducts().map {
                    it.toProduct()
                }
            } else {
                return service.getProductsByCategory(category).map {
                    it.toProduct()
                }
            }

        } catch (e: IOException) {
            throw BusinessException.IOException("IOException: ${e.message}")
        } catch (e: HttpException) {
            throw BusinessException.NetworkException("NetworkException: ${e.message}")
        }
    }
}