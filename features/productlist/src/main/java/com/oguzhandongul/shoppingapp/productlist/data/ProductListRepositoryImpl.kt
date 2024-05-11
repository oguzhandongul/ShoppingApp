package com.oguzhandongul.shoppingapp.productlist.data

import com.oguzhandongul.shoppingapp.core.util.utils.JsonFileParser
import com.oguzhandongul.shoppingapp.product.model.Product
import com.oguzhandongul.shoppingapp.product.model.ProductResponse
import com.oguzhandongul.shoppingapp.product.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.oguzhandongul.shoppingapp.product.R

class ProductListRepositoryImpl @Inject constructor(private val jsonFileParser: JsonFileParser) :
    ProductRepository {

    override suspend fun getProductList(): Result<List<Product>> {
        return try {
            withContext(Dispatchers.IO) {
                val productResponse =
                    jsonFileParser.parseRawFileToObject<ProductResponse>(R.raw.products)
                if (productResponse != null) {
                    Result.success(productResponse.products)
                } else {
                    Result.failure(Exception("Response is null"))
                }
            }
        } catch (exception: Exception) {
            // Handle JSON parsing error (e.g., log, show error message)
            Result.failure(exception)
        }
    }
}