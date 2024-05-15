package com.oguzhandongul.shoppingapp.productlist.usecases

import com.oguzhandongul.shoppingapp.core.util.utils.Resource
import com.oguzhandongul.shoppingapp.product.repository.ProductRepository
import com.oguzhandongul.shoppingapp.productlist.domain.usecase.CacheProductListUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class CacheProductListUseCaseTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    private lateinit var cacheProductListUseCase: CacheProductListUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        cacheProductListUseCase = CacheProductListUseCase(productRepository)
    }

    @Test
    fun `invoke should emit Loading and then Success`() = runBlockingTest {
        // Mock the repository to return a successful Result
        `when`(productRepository.cacheProductList()).thenReturn(Result.success(Unit))

        val result = cacheProductListUseCase().first()

        assertTrue(result is Resource.Loading)
        val finalResult = cacheProductListUseCase().toList()
        assertTrue(finalResult[1] is Resource.Success)
    }

    @Test
    fun `invoke should emit Loading and then Error on failure`() = runBlockingTest {
        val exception = RuntimeException("Failed to cache product list")

        // Mock the repository to return a failed Result
        `when`(productRepository.cacheProductList()).thenReturn(Result.failure(exception))

        val result = cacheProductListUseCase().toList()

        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Error)
        assertEquals("Failed to cache product list", (result[1] as Resource.Error).message)
    }

    @Test
    fun `invoke should emit Loading and then Error on exception`() = runBlockingTest {
        val exceptionMessage = "Unknown error occurred"
        // Mock the repository to throw an exception
        `when`(productRepository.cacheProductList()).thenThrow(RuntimeException(exceptionMessage))

        val result = cacheProductListUseCase().toList()

        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Error)
        assertEquals(exceptionMessage, (result[1] as Resource.Error).message)
    }
}