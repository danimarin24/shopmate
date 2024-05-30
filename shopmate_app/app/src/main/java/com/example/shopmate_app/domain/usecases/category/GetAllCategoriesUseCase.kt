package com.example.shopmate_app.domain.usecases.category

import com.example.shopmate_app.domain.repo.CategoryRepository
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(private val repository: CategoryRepository){
    suspend operator fun invoke() = repository.getAllCategories()
}