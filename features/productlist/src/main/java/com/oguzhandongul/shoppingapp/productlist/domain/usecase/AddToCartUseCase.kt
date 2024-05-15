package com.oguzhandongul.shoppingapp.productlist.domain.usecase

import com.oguzhandongul.shoppingapp.core.util.utils.Resource
import com.oguzhandongul.shoppingapp.product.model.Product
import com.oguzhandongul.shoppingapp.product.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Use case for adding a product to the cart.
 *
 * @property cartRepository The repository that manages cart operations.
 */
class AddToCartUseCase @Inject constructor(
     private val cartRepository: CartRepository
) {
     /**
      * Adds a product to the cart and returns a Flow emitting the state of the operation.
      *
      * @param product The product to be added to the cart.
      * @return A Flow emitting [Resource] indicating the loading, success, or error state.
      */
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