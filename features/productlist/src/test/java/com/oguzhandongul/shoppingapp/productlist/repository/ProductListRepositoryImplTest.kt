package com.oguzhandongul.shoppingapp.productlist.repository

import com.oguzhandongul.shoppingapp.productlist.data.ProductListRepositoryImpl
import org.junit.Test
import org.junit.Assert.*
import com.oguzhandongul.shoppingapp.core.util.utils.JsonFileParser
import com.oguzhandongul.shoppingapp.product.model.ProductResponse
import com.oguzhandongul.shoppingapp.product.local.ProductDao
import com.oguzhandongul.shoppingapp.productlist.mocks.MockProductData
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before

@ExperimentalCoroutinesApi
class ProductListRepositoryImplTest {

    @MockK
    private lateinit var jsonFileParser: JsonFileParser
    @MockK
    private lateinit var productDao: ProductDao
    private lateinit var productListRepository: ProductListRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        productListRepository = ProductListRepositoryImpl(jsonFileParser, productDao)
    }

    @Test
    fun `getProductList should return products from database`() = runTest {
        val expectedProducts = listOf(MockProductData.getMockProduct("1"))
        coEvery { productDao.getAllProducts() } returns flowOf(expectedProducts)

        val result = productListRepository.getProductList().first()

        assertEquals(expectedProducts, result)
    }

    @Test
    fun `cacheProductList should not cache products when products already exist`() = runTest {
        val existingProductList = listOf(MockProductData.getMockProduct("1"))

        coEvery { productDao.getAllProducts() } returns flowOf(existingProductList)

        val result = productListRepository.cacheProductList()

        coVerify(exactly = 0) { productDao.insertAll(any()) }
        assertTrue(result.isSuccess)
    }

    @Test
    fun `cacheProductList should return failure when parsing fails`() = runTest {
        coEvery { productDao.getAllProducts() } returns flowOf(emptyList())
        every { jsonFileParser.parseRawFileToObject<ProductResponse>(com.oguzhandongul.shoppingapp.product.R.raw.products) } returns null

        val result = productListRepository.cacheProductList()

        assertTrue(result.isFailure)
    }

    @Test
    fun `cacheProductList should return failure when exception occurs`() = runTest {
        coEvery { productDao.getAllProducts() } returns flowOf(emptyList())
        every { jsonFileParser.parseRawFileToObject<ProductResponse>(com.oguzhandongul.shoppingapp.product.R.raw.products) } throws RuntimeException("Test Exception")

        val result = productListRepository.cacheProductList()

        assertTrue(result.isFailure)
    }

    @Test
    fun `getProductById should return the correct product`() = runTest {
        val productId = "1"
        val expectedProduct = MockProductData.getMockProduct(productId)

        coEvery { productDao.getProductById(productId) } returns expectedProduct

        val result = productListRepository.getProductById(productId)

        assertEquals(expectedProduct, result)
    }

    @Test
    fun `getProductById should return null if product not found`() = runTest {
        val productId = "1"
        coEvery { productDao.getProductById(productId) } returns null

        val result = productListRepository.getProductById(productId)

        assertNull(result)
    }

}