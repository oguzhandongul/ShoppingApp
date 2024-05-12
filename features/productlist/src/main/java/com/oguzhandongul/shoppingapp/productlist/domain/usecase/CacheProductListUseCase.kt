package com.oguzhandongul.shoppingapp.productlist.domain.usecase

import com.oguzhandongul.shoppingapp.core.util.utils.Resource
import com.oguzhandongul.shoppingapp.product.model.Product
import com.oguzhandongul.shoppingapp.product.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CacheProductListUseCase @Inject constructor(
     private val productRepository: ProductRepository
) {
     suspend operator fun invoke(): Flow<Resource<Unit>> = flow {
          emit(Resource.Loading())
          try {
               val result = productRepository.cacheProductList()
               if (result.isSuccess) {
                    emit(Resource.Success(Unit))
               } else {
                    val exception = result.exceptionOrNull()
                    emit(Resource.Error(exception?.message ?: "Unknown error"))
               }
          } catch (e: Exception) {
               emit(Resource.Error(e.message ?: "Unknown error"))
          }
     }
}