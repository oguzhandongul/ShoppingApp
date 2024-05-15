package com.oguzhandongul.shoppingapp.productlist.data

import com.oguzhandongul.shoppingapp.core.util.utils.JsonFileParser
import com.oguzhandongul.shoppingapp.product.model.Product
import com.oguzhandongul.shoppingapp.product.model.ProductResponse
import com.oguzhandongul.shoppingapp.product.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.oguzhandongul.shoppingapp.product.R
import com.oguzhandongul.shoppingapp.product.local.ProductDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class ProductListRepositoryImpl @Inject constructor(
    private val jsonFileParser: JsonFileParser,
    private val productDao: ProductDao
) :
    ProductRepository {

    override fun getProductList(): Flow<List<Product>> {
        return productDao.getAllProducts()
    }
    override suspend fun cacheProductList(): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val products = productDao.getAllProducts().first()
                if (products.isEmpty()) {
                    val productResponse = jsonFileParser.parseRawFileToObject<ProductResponse>(R.raw.products)
                    if (productResponse != null) {
                        productDao.insertAll(productResponse.products)
                        Result.success(Unit) // Successfully cached
                    } else {
                        Result.failure(Exception("Failed to parse products"))
                    }
                } else {
                    Result.success(Unit) // No need to cache as data already exists
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getProductById(productId: String): Product? = withContext(Dispatchers.IO) {
        productDao.getProductById(productId)
    }
}