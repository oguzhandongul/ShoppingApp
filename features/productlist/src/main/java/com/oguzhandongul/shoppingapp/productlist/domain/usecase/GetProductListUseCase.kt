package com.oguzhandongul.shoppingapp.productlist.domain.usecase

import com.oguzhandongul.shoppingapp.product.model.Product
import com.oguzhandongul.shoppingapp.product.repository.ProductRepository
import javax.inject.Inject

class GetProductListUseCase @Inject constructor(private val productRepository: ProductRepository) {

    suspend operator fun invoke(): Result<List<Product>> = productRepository.getProductList()

}