package com.luizalabs.logistica.desafio.core.service

import com.luizalabs.logistica.desafio.core.model.entity.CustomerEntity
import com.luizalabs.logistica.desafio.dataprovider.customer.CustomerSpecification
import com.luizalabs.logistica.desafio.dataprovider.customer.CustomerRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CustomerService(
    private val customerRepository: CustomerRepository
) {

    fun saveAll(customers: List<CustomerEntity>): List<CustomerEntity> {
        return customerRepository.saveAll(customers)
    }

    fun findCustomersByOrdersParams(dateFrom: LocalDate?, dateTo: LocalDate?, orderId: Long?): List<CustomerEntity> {
        val spec = CustomerSpecification.hasOrders(dateFrom, dateTo, orderId)
        return customerRepository.findAll(spec)
    }
}
