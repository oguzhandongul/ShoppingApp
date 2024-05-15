package com.oguzhandongul.shoppingapp.productlist.usecases

import com.oguzhandongul.shoppingapp.core.util.utils.Resource
import com.oguzhandongul.shoppingapp.product.repository.CartRepository
import com.oguzhandongul.shoppingapp.productlist.mocks.MockProductData
import com.oguzhandongul.shoppingapp.productlist.domain.usecase.AddToCartUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals
import kotlin.test.assertTrue


@ExperimentalCoroutinesApi
class AddToCartUseCaseTest {

    @Mock
    private lateinit var cartRepository: CartRepository

    private lateinit var addToCartUseCase: AddToCartUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        addToCartUseCase = AddToCartUseCase(cartRepository)
    }

    @Test
    fun `invoke should emit Loading and then Success`() = runBlockingTest {
        val product = MockProductData.getMockProduct("1")

        // Mock the suspend function to do nothing (success case)
        `when`(cartRepository.addToCart(product)).thenReturn(Unit)

        val result = addToCartUseCase(product).first()

        assertTrue(result is Resource.Loading)
        val finalResult = addToCartUseCase(product).toList()
        assertTrue(finalResult[1] is Resource.Success)
    }

    @Test
    fun `invoke should emit Loading and then Error on failure`() = runBlockingTest {
        val product = MockProductData.getMockProduct("1")
        val exception = RuntimeException("Failed to add to cart")

        // Mock the suspend function to throw an exception
        `when`(cartRepository.addToCart(product)).thenThrow(exception)

        val result = addToCartUseCase(product).toList()

        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Error)
        assertEquals("Failed to add to cart: ${exception.message}", (result[1] as Resource.Error).message)
    }
}