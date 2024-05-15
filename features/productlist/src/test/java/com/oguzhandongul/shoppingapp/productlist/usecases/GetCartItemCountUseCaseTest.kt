package com.oguzhandongul.shoppingapp.productlist.usecases

import com.oguzhandongul.shoppingapp.product.repository.CartRepository
import com.oguzhandongul.shoppingapp.productlist.mocks.MockProductData
import com.oguzhandongul.shoppingapp.productlist.domain.usecase.GetCartItemCountUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class GetCartItemCountUseCaseTest {

    @Mock
    private lateinit var cartRepository: CartRepository

    private lateinit var getCartItemCountUseCase: GetCartItemCountUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getCartItemCountUseCase = GetCartItemCountUseCase(cartRepository)
    }

    @Test
    fun `invoke should return correct item count`() = runBlockingTest {
        val cartItems = listOf(
            MockProductData.getMockCartItem("1", 2),
            MockProductData.getMockCartItem("2", 3)
        )

        // Mock the repository to return the list of cart items
        `when`(cartRepository.getCartItems()).thenReturn(flowOf(cartItems))

        val itemCount = getCartItemCountUseCase().first()

        assertEquals(5, itemCount)
    }

    @Test
    fun `invoke should return zero when cart is empty`() = runBlockingTest {
        // Mock the repository to return an empty list of cart items
        `when`(cartRepository.getCartItems()).thenReturn(flowOf(emptyList()))

        val itemCount = getCartItemCountUseCase().first()

        assertEquals(0, itemCount)
    }

    @Test
    fun `invoke should handle large quantities correctly`() = runBlockingTest {
        val cartItems = listOf(
            MockProductData.getMockCartItem("1", 1000),
            MockProductData.getMockCartItem("2", 2000),
            MockProductData.getMockCartItem("3", 3000)
        )

        // Mock the repository to return the list of cart items
        `when`(cartRepository.getCartItems()).thenReturn(flowOf(cartItems))

        val itemCount = getCartItemCountUseCase().first()

        assertEquals(6000, itemCount)
    }
}