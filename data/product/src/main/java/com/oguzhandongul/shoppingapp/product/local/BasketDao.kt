package com.oguzhandongul.shoppingapp.product.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.oguzhandongul.shoppingapp.product.model.BasketItem
import kotlinx.coroutines.flow.Flow

@Dao
interface BasketDao {
    @Query("SELECT * FROM basket_items")
    fun getAllBasketItems(): Flow<List<BasketItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Or choose your conflict resolution strategy
    suspend fun insertBasketItem(basketItem: BasketItem)

    @Update
    suspend fun updateBasketItem(basketItem: BasketItem)

    @Delete
    suspend fun deleteBasketItem(basketItem: BasketItem)

    @Query("SELECT * FROM basket_items WHERE productId = :productId")
    suspend fun getBasketItemByProductId(productId: String): BasketItem?
}