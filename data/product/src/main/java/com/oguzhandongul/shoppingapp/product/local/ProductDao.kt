package com.oguzhandongul.shoppingapp.product.local

import androidx.room.*
import com.oguzhandongul.shoppingapp.product.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE id = :productId")
    suspend fun getProductById(productId: String): Product?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<Product>)

    @Update
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)
}