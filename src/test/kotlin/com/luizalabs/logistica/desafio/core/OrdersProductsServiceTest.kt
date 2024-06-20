package com.luizalabs.logistica.desafio.core

import com.luizalabs.logistica.desafio.core.model.entity.*
import com.luizalabs.logistica.desafio.core.service.OrdersProductsService
import com.luizalabs.logistica.desafio.dataprovider.ordersProducts.OrdersProductsRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class OrdersProductsServiceTest {

    @Mock
    private lateinit var ordersProductsRepository: OrdersProductsRepository

    @InjectMocks
    private lateinit var ordersProductsService: OrdersProductsService

    @Test
    fun `should save all ordersProducts`() {
        val ordersProductsEntity1 = buildOrdersProductsEntity(1L)
        val ordersProductsEntity2 = buildOrdersProductsEntity(2L)
        val ordersProductsList = listOf(ordersProductsEntity1, ordersProductsEntity2)

        Mockito.`when`(ordersProductsRepository.saveAll(ordersProductsList)).thenReturn(ordersProductsList)

        val result = ordersProductsService.saveAll(ordersProductsList)

        assertEquals(ordersProductsList, result)
    }

    private fun buildOrdersProductsEntity(id: Long) = OrdersProductsEntity(
        id = id,
        order = buildOrder(id),
        product = buildProduct(id),
        value = buildValue()
    )

    private fun buildOrder(id: Long) = OrderEntity(
        date = LocalDate.now(),
        customer = buildCustomer(id),
        externalIdFromImportedFile = 10,
        importedFile = buildImportedFile()
    )

    private fun buildProduct(id: Long) = ProductEntity(
        id = id,
        externalIdFromImportedFile = 10,
        importedFile = buildImportedFile()
    )

    private fun buildValue() = BigDecimal.valueOf(1.0)

    private fun buildCustomer(id: Long) = CustomerEntity(
        name = "teste",
        externalIdFromImportedFile = 10,
        importedFile = buildImportedFile()
    )

    private fun buildImportedFile() = ImportedFileEntity(
        id = 1L,
        name = "test_file"
    )
}