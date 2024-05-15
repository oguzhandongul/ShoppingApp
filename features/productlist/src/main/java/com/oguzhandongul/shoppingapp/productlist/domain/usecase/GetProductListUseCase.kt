package com.oguzhandongul.shoppingapp.productlist.domain.usecase

import com.oguzhandongul.shoppingapp.core.util.utils.Resource
import com.oguzhandongul.shoppingapp.product.model.Product
import com.oguzhandongul.shoppingapp.product.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetProductListUseCase @Inject constructor(
     private val productRepository: ProductRepository
) {
     operator fun invoke(): Flow<Resource<List<Product>>> = flow {
          emit(Resource.Loading())
          try {
               emitAll(productRepository.getProductList().map { products ->
                    Resource.Success(products)
               })
          } catch (e: Exception) {
               emit(Resource.Error(e.message ?: "Unknown error"))
          }
     }
}