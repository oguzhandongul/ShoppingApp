package com.oguzhandongul.shoppingapp.features.productdetail.domain.usecases

import com.oguzhandongul.shoppingapp.core.util.utils.Resource
import com.oguzhandongul.shoppingapp.product.model.Product
import com.oguzhandongul.shoppingapp.product.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
     private val cartRepository: CartRepository
) {
     suspend operator fun invoke(product: Product): Flow<Resource<Unit>> = flow {
          emit(Resource.Loading())
          try {
               cartRepository.addToCart(product)
               emit(Resource.Success(Unit)) // Indicate success with no data
          } catch (e: Exception) {
               emit(Resource.Error("Failed to add to cart: ${e.message}")) // Handle error
          }
     }
}