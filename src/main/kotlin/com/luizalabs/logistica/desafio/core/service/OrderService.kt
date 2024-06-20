package com.luizalabs.logistica.desafio.core.service

import com.luizalabs.logistica.desafio.core.model.dto.OrderImportRequestDto
import com.luizalabs.logistica.desafio.core.model.entity.OrderEntity
import com.luizalabs.logistica.desafio.dataprovider.order.OrderRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val importedFileService: ImportedFileService,
    private val orderRepository: OrderRepository,
    private val customerService: CustomerService,
    private val productService: ProductService,
    private val ordersProductService: OrdersProductsService

) {
    private fun saveAll(orders: List<OrderEntity>): List<OrderEntity> {
        return orderRepository.saveAll(orders)
    }

    @Transactional
    fun importOrders(fileName: String, orderImportRequestsDto: List<OrderImportRequestDto>): List<OrderEntity> {
        // Save file name imported
        val importedFileSaved = importedFileService.save(fileName)

        // Save customers
        val customers = orderImportRequestsDto
            .distinctBy { it.userId }
            .map { it.toCustomerEntity(importedFileSaved) }
        val customersSaved = customerService.saveAll(customers)

        // Save orders
        val orders = orderImportRequestsDto
            .distinctBy { it.orderId }
            .map { orderImportRequestDto ->
                val customer = customersSaved.find { it.externalIdFromImportedFile == orderImportRequestDto.userId }!!
                orderImportRequestDto.toOrderEntity(customer, importedFileSaved)
            }
        val ordersSaved = saveAll(orders)

        // Save products
        val products = orderImportRequestsDto
            .distinctBy { it.productId }
            .map { it.toProductEntity(importedFileSaved) }

        val productsSaved = productService.saveAll(products)

        // Save orders and products relationship
        val ordersProducts = orderImportRequestsDto.map { orderImportRequestDto ->
            val order = ordersSaved.find { it.externalIdFromImportedFile == orderImportRequestDto.orderId }!!
            val product = productsSaved.find { it.externalIdFromImportedFile == orderImportRequestDto.productId }!!
            orderImportRequestDto.toOrdersProductsEntity(order, product)
        }
        ordersProductService.saveAll(ordersProducts)

        return ordersSaved
    }
}