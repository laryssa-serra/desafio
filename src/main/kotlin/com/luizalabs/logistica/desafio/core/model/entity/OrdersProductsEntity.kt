package com.luizalabs.logistica.desafio.core.model.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "ORDERS_PRODUCTS", uniqueConstraints = [UniqueConstraint(columnNames = ["order_id", "product_id"])])
class OrdersProductsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_products_seq")
    @SequenceGenerator(name = "orders_products_seq", sequenceName = "orders_products_id_seq", allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    val order: OrderEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    val product: ProductEntity,

    @Column(name = "value", nullable = false)
    val value: BigDecimal
)