package com.luizalabs.logistica.desafio.core

import com.luizalabs.logistica.desafio.core.model.entity.ImportedFileEntity
import com.luizalabs.logistica.desafio.core.model.entity.ProductEntity
import com.luizalabs.logistica.desafio.core.service.ProductService
import com.luizalabs.logistica.desafio.dataprovider.product.ProductRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ProductServiceTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    @InjectMocks
    private lateinit var productService: ProductService

    @Test
    fun `should save all products`() {
        val productEntity1 = buildProduct(1L)
        val productEntity2 = buildProduct(2L)
        val productsList = listOf(productEntity1, productEntity2)

        Mockito.`when`(productRepository.saveAll(productsList)).thenReturn(productsList)

        val result = productService.saveAll(productsList)

        assertEquals(productsList, result)
    }

    private fun buildProduct(id: Long) = ProductEntity(
        id = id,
        externalIdFromImportedFile = 10,
        importedFile = buildImportedFile()
    )

    private fun buildImportedFile() = ImportedFileEntity(
        id = 1L,
        name = "test_file"
    )
}