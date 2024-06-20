package com.luizalabs.logistica.desafio.dataprovider.order

import com.luizalabs.logistica.desafio.core.model.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<OrderEntity, Long>