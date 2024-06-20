package com.luizalabs.logistica.desafio.core

import com.luizalabs.logistica.desafio.core.model.entity.CustomerEntity
import com.luizalabs.logistica.desafio.core.model.entity.ImportedFileEntity
import com.luizalabs.logistica.desafio.core.model.entity.OrderEntity
import com.luizalabs.logistica.desafio.core.service.CustomerService
import com.luizalabs.logistica.desafio.dataprovider.customer.CustomerRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.Captor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class CustomerServiceTest {

    @Mock
    private lateinit var customerRepository: CustomerRepository

    @Captor
    private lateinit var specificationCaptor: ArgumentCaptor<Specification<CustomerEntity>>

    @InjectMocks
    private lateinit var customerService: CustomerService

    private lateinit var customer1: CustomerEntity
    private lateinit var customer2: CustomerEntity

    @BeforeEach
    fun setUp() {
        customer1 = buildCustomerEntity(1L, "Customer 1")
        customer2 = buildCustomerEntity(2L, "Customer 2")
    }

    @Test
    fun `should save all customers`() {
        val expectedCustomers = listOf(customer1, customer2)
        `when`(customerRepository.saveAll(expectedCustomers)).thenReturn(expectedCustomers)

        val savedCustomers = customerService.saveAll(expectedCustomers)
        assertEquals(expectedCustomers, savedCustomers)
    }

    @Test
    fun `should find customers by order parameters`() {
        val dateFrom: LocalDate? = LocalDate.of(2022, 1, 1)
        val dateTo: LocalDate? = LocalDate.of(2022, 12, 31)
        val orderId = 4L

        val expectedCustomers = listOf(customer1, customer2)

        `when`(customerRepository.findAll(any<Specification<CustomerEntity>>())).thenReturn(expectedCustomers)

        val actualCustomers = customerService.findCustomersByOrdersParams(dateFrom, dateTo, orderId)

        verify(customerRepository).findAll(specificationCaptor.capture())

        assertEquals(expectedCustomers, actualCustomers)
    }

    private fun buildCustomerEntity(
        id: Long,
        name: String,
        orders: List<OrderEntity> = emptyList(),
        externalIdFromImportedFile: Int = 0,
        importedFile: ImportedFileEntity = ImportedFileEntity()
    ): CustomerEntity {
        return CustomerEntity(
            id = id,
            name = name,
            orders = orders,
            externalIdFromImportedFile = externalIdFromImportedFile,
            importedFile = importedFile
        )
    }
}

