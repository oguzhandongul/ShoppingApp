package com.oguzhandongul.shoppingapp.features.basket.data

import com.oguzhandongul.shoppingapp.product.local.BasketDao
import com.oguzhandongul.shoppingapp.product.model.BasketItem
import com.oguzhandongul.shoppingapp.product.model.Product
import com.oguzhandongul.shoppingapp.product.repository.BasketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BasketRepositoryImpl @Inject constructor(private val basketDao: BasketDao) : BasketRepository {
    override fun getBasketItems(): Flow<List<BasketItem>> = basketDao.getAllBasketItems()

    override suspend fun addToBasket(product: Product) {
        val basketItem = BasketItem(productId = product.id, quantity = 1)
        basketDao.insertBasketItem(basketItem)
    }

    override suspend fun removeFromBasket(product: Product) {
        val basketItem = basketDao.getBasketItemByProductId(product.id)
        if (basketItem != null) {
            basketDao.deleteBasketItem(basketItem)
        }
    }

    override suspend fun clearBasket() {
        //TODO basketDao.deleteAllBasketItems()
    }
}