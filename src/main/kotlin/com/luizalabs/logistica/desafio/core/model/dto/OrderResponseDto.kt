package com.luizalabs.logistica.desafio.core.model.dto

import com.luizalabs.logistica.desafio.core.model.entity.OrderEntity
import java.math.BigDecimal
import java.time.LocalDate

data class OrderResponseDto(
    val order_id: Long,
    val date: LocalDate,
    val total: BigDecimal,
    val products: List<OrdersProductsResponseDto>
)

fun OrderEntity.toResponse() = OrderResponseDto(
    order_id = id,
    date = date,
    total = ordersProducts.sumOf { it.value }.setScale(2),
    products = ordersProducts.map { it.toResponse() }
)