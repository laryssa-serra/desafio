package com.luizalabs.logistica.desafio.core.model.dto

import com.luizalabs.logistica.desafio.core.model.entity.OrdersProductsEntity
import java.math.BigDecimal

data class OrdersProductsResponseDto(
    val product_id: Long,
    val value: BigDecimal
)

fun OrdersProductsEntity.toResponse() = OrdersProductsResponseDto(
    product_id = id,
    value = value.setScale(2)
)