package com.oguzhandongul.shoppingapp.productlist.usecases

import com.oguzhandongul.shoppingapp.core.util.utils.Resource
import com.oguzhandongul.shoppingapp.product.repository.ProductRepository
import com.oguzhandongul.shoppingapp.productlist.domain.usecase.GetProductListUseCase
import com.oguzhandongul.shoppingapp.productlist.mocks.MockProductData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
class GetProductListUseCaseTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    private lateinit var getProductListUseCase: GetProductListUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getProductListUseCase = GetProductListUseCase(productRepository)
    }

    @Test
    fun `invoke should emit Loading and then Success`() = runBlockingTest {
        val products = listOf(
            MockProductData.getMockProduct("1"),
            MockProductData.getMockProduct("2")
        )

        // Mock the repository to return the list of products
        `when`(productRepository.getProductList()).thenReturn(flowOf(products))

        val result = getProductListUseCase().toList()

        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)
        assertEquals(products, (result[1] as Resource.Success).data)
    }

    @Test
    fun `invoke should emit Loading and then Error on failure`() = runBlockingTest {
        val exception = RuntimeException("Failed to fetch product list")

        // Mock the repository to throw an exception
        `when`(productRepository.getProductList()).thenThrow(exception)

        val result = getProductListUseCase().toList()

        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Error)
        assertEquals("Failed to fetch product list", (result[1] as Resource.Error).message)
    }

    @Test
    fun `invoke should handle empty product list`() = runBlockingTest {
        // Mock the repository to return an empty list
        `when`(productRepository.getProductList()).thenReturn(flowOf(emptyList()))

        val result = getProductListUseCase().toList()

        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)
        (result[1] as Resource.Success).data?.let { assertTrue(it.isEmpty()) }
    }
}