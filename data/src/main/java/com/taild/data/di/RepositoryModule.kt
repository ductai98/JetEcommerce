package com.taild.data.di

import com.taild.data.repository.CategoryRepositoryImpl
import com.taild.data.repository.ProductRepositoryImpl
import com.taild.domain.repository.CategoryRepository
import com.taild.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindProductRepository(repository: ProductRepositoryImpl): ProductRepository

    @Binds
    abstract fun bindCategoryRepository(repository: CategoryRepositoryImpl): CategoryRepository
}