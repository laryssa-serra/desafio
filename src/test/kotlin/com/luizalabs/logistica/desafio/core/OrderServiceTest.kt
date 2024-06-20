package com.luizalabs.logistica.desafio.core

import com.luizalabs.logistica.desafio.core.model.dto.OrderImportRequestDto
import com.luizalabs.logistica.desafio.core.model.entity.*
import com.luizalabs.logistica.desafio.core.service.*
import com.luizalabs.logistica.desafio.dataprovider.order.OrderRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@ExtendWith(MockitoExtension::class)
class OrderServiceTest {
    @Mock
    private lateinit var importedFileService: ImportedFileService

    @Mock
    private lateinit var orderRepository: OrderRepository

    @Mock
    private lateinit var customerService: CustomerService

    @Mock
    private lateinit var productService: ProductService

    @Mock
    private lateinit var ordersProductService: OrdersProductsService

    @InjectMocks
    private lateinit var orderService: OrderService

    private lateinit var orderImportRequestDto: OrderImportRequestDto
    private lateinit var importedFileEntity: ImportedFileEntity
    private lateinit var customerEntity: CustomerEntity
    private lateinit var orderEntity: OrderEntity
    private lateinit var productEntity: ProductEntity
    private lateinit var ordersProductsEntity: OrdersProductsEntity

    @BeforeEach
    fun setUp() {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse("2022-01-01", formatter)
        val fromDouble = BigDecimal(123.45)

        orderImportRequestDto = OrderImportRequestDto(1, "teste", 10, 10, fromDouble, date)
        importedFileEntity = ImportedFileEntity(id = 1L, name = "test_file")
        customerEntity = CustomerEntity(name = "Customer", externalIdFromImportedFile = 1, importedFile = importedFileEntity)
        orderEntity = OrderEntity(date = date, customer = customerEntity, externalIdFromImportedFile = 10, importedFile = importedFileEntity)
        productEntity = ProductEntity(id = 1L, externalIdFromImportedFile = 10, importedFile = importedFileEntity)
        ordersProductsEntity = OrdersProductsEntity(id = 1L, order = orderEntity, product = productEntity, fromDouble)
    }

    @Test
    fun `should import orders`() {
        val fileName = "test_file"
        val orderImportRequestsDto = listOf(orderImportRequestDto)

        `when`(importedFileService.save(fileName)).thenReturn(importedFileEntity)
        `when`(customerService.saveAll(Mockito.anyList())).thenReturn(listOf(customerEntity))
        `when`(orderRepository.saveAll(Mockito.anyList())).thenReturn(listOf(orderEntity))
        `when`(productService.saveAll(Mockito.anyList())).thenReturn(listOf(productEntity))
        `when`(ordersProductService.saveAll(Mockito.anyList())).thenReturn(listOf(ordersProductsEntity))

        val orders = orderService.importOrders(fileName, orderImportRequestsDto)

        assertEquals(listOf(orderEntity), orders)
    }
}