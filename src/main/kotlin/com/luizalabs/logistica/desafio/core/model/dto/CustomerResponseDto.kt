package com.luizalabs.logistica.desafio.core.model.dto

import com.luizalabs.logistica.desafio.core.model.entity.CustomerEntity

data class CustomerResponseDto(
    val name: String,
    val user_id: Long,
    val orders: List<OrderResponseDto>
)

fun CustomerEntity.toResponse() = CustomerResponseDto(
    name = name,
    user_id = id,
    orders = orders.map { it.toResponse() }
)