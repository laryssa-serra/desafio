package com.luizalabs.logistica.desafio.dataprovider.ordersProducts

import com.luizalabs.logistica.desafio.core.model.entity.OrdersProductsEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrdersProductsRepository : JpaRepository<OrdersProductsEntity, Long>