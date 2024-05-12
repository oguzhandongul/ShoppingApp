package com.oguzhandongul.shoppingapp.productlist.domain.usecase

import com.oguzhandongul.shoppingapp.core.util.utils.Resource
import com.oguzhandongul.shoppingapp.product.model.Product
import com.oguzhandongul.shoppingapp.product.repository.BasketRepository
import com.oguzhandongul.shoppingapp.product.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AddToBasketUseCase @Inject constructor(
     private val basketRepository: BasketRepository
) {
     suspend operator fun invoke(product: Product): Flow<Resource<Unit>> = flow {
          emit(Resource.Loading())
          try {
               basketRepository.addToBasket(product)
               emit(Resource.Success(Unit)) // Indicate success with no data
          } catch (e: Exception) {
               emit(Resource.Error("Failed to add to basket: ${e.message}")) // Handle error
          }
     }
}