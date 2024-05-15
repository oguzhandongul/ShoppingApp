package com.oguzhandongul.shoppingapp.features.productdetail.domain.usecases

import com.oguzhandongul.shoppingapp.core.util.utils.Resource
import com.oguzhandongul.shoppingapp.product.model.Product
import com.oguzhandongul.shoppingapp.product.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(productId: String): Flow<Resource<Product>> = flow {
        emit(Resource.Loading())
        val product = productRepository.getProductById(productId) // You need to add this function to the repository
        emit(if (product != null) Resource.Success(product) else Resource.Error("Product not found"))
    }
}