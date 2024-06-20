package com.luizalabs.logistica.desafio.core.model.dto

import com.luizalabs.logistica.desafio.core.model.entity.*
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class OrderImportRequestDto(
    val userId: Int,
    val name: String,
    val orderId: Int,
    val productId: Int,
    val value: BigDecimal,
    val date: LocalDate,
) {
    fun toCustomerEntity(importedFile: ImportedFileEntity) = CustomerEntity(
        name = name,
        externalIdFromImportedFile = userId,
        importedFile = importedFile
    )

    fun toOrderEntity(customer: CustomerEntity, importedFile: ImportedFileEntity) = OrderEntity(
        date = date,
        customer = customer,
        externalIdFromImportedFile = orderId,
        importedFile = importedFile
    )

    fun toProductEntity(importedFile: ImportedFileEntity) = ProductEntity(
        externalIdFromImportedFile = productId,
        importedFile = importedFile
    )

    fun toOrdersProductsEntity(order: OrderEntity, product: ProductEntity) = OrdersProductsEntity(
        order = order,
        product = product,
        value = value
    )
}

fun String.toOrderImportRequestDto(): OrderImportRequestDto {
    val userId = this.substring(0, 10).toInt()
    val name = this.substring(10, 55).trim()
    val orderId = this.substring(55, 65).toInt()
    val productId = this.substring(65, 75).toInt()
    val value = BigDecimal(this.substring(75, 87).trim())
    val orderDateString = this.substring(87).trim()

    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val orderDate = LocalDate.parse(orderDateString, formatter)

    return OrderImportRequestDto(userId, name, orderId, productId, value, orderDate)
}