package com.luizalabs.logistica.desafio.core.service

import com.luizalabs.logistica.desafio.core.model.entity.OrdersProductsEntity
import com.luizalabs.logistica.desafio.dataprovider.ordersProducts.OrdersProductsRepository
import org.springframework.stereotype.Service

@Service
class OrdersProductsService(
    private val ordersProductsRepository: OrdersProductsRepository
) {

    fun saveAll(ordersProducts: List<OrdersProductsEntity>): List<OrdersProductsEntity> {
        return ordersProductsRepository.saveAll(ordersProducts)
    }
}