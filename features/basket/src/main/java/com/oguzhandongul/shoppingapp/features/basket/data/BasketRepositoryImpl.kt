package com.oguzhandongul.shoppingapp.features.basket.data

import com.oguzhandongul.shoppingapp.product.local.BasketDao
import com.oguzhandongul.shoppingapp.product.model.BasketItem
import com.oguzhandongul.shoppingapp.product.model.Product
import com.oguzhandongul.shoppingapp.product.repository.BasketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BasketRepositoryImpl @Inject constructor(private val basketDao: BasketDao) :
    BasketRepository {
    override fun getBasketItems(): Flow<List<BasketItem>> = basketDao.getAllBasketItems()

    override suspend fun addToBasket(product: Product) {
        val existingBasketItem = basketDao.getBasketItemByProductId(product.id)
        if (existingBasketItem != null) {
            // Update existing item's quantity
            basketDao.updateBasketItem(existingBasketItem.copy(quantity = existingBasketItem.quantity + 1))
        } else {
            // Insert a new basket item with the product details
            basketDao.insertBasketItem(BasketItem(productId = product.id, quantity = 1, product = product))
        }
    }

    override suspend fun removeFromBasket(productId: String) {
        val basketItem = basketDao.getBasketItemByProductId(productId)
        if (basketItem != null) {
            basketDao.deleteBasketItem(basketItem)
        }
    }

    override suspend fun updateBasketItemQuantity(basketItem: BasketItem) {
        basketDao.updateBasketItem(basketItem)
    }

    override suspend fun clearBasket() {
        basketDao.deleteAllBasketItems()
    }
}