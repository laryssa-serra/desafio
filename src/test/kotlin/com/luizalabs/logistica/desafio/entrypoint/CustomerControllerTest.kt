package com.luizalabs.logistica.desafio.entrypoint

import com.luizalabs.logistica.desafio.core.model.dto.CustomerResponseDto
import com.luizalabs.logistica.desafio.core.model.entity.CustomerEntity
import com.luizalabs.logistica.desafio.core.model.entity.ImportedFileEntity
import com.luizalabs.logistica.desafio.core.model.entity.OrderEntity
import com.luizalabs.logistica.desafio.core.service.CustomerService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class CustomerControllerTest {

    @Mock
    private lateinit var customerService: CustomerService

    @InjectMocks
    private lateinit var customerController: CustomerController

    private lateinit var customerEntity: CustomerEntity

    @BeforeEach
    fun setUp() {
        customerEntity = CustomerEntity(
            name = "Customer 1",
            externalIdFromImportedFile = 1,
            importedFile = null
        )
        customerEntity.id = 1L

        val orderEntity = OrderEntity(
            date = LocalDate.of(2022, 1, 1),
            customer = customerEntity,
            externalIdFromImportedFile = 1,
            importedFile = ImportedFileEntity()
        )
        orderEntity.id = 1L

        customerEntity.orders = listOf(orderEntity)
    }

    @Test
    fun `should return list of customers`() {
        Mockito.`when`(customerService.findCustomersByOrdersParams(any(), any(), any()))
            .thenReturn(listOf(customerEntity))

        val response: ResponseEntity<List<CustomerResponseDto>> =
            customerController.getAllCustomersResponse(any(), any(), any())

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(1, response.body?.size)
        assertEquals("Customer 1", response.body?.get(0)?.name)

        Mockito.verify(customerService).findCustomersByOrdersParams(any(), any(), any())
    }

    @Test
    fun `should handle internal server error`() {
        Mockito.`when`(customerService.findCustomersByOrdersParams(any(), any(), any()))
            .thenThrow(RuntimeException("Internal Server Error"))

        val response: ResponseEntity<List<CustomerResponseDto>> =
            customerController.getAllCustomersResponse(any(), any(), any())

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
        Mockito.verify(customerService).findCustomersByOrdersParams(any(), any(), any())
    }
}