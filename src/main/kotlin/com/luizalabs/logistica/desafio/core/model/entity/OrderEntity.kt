package com.luizalabs.logistica.desafio.core.model.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "ORDERS")
data class OrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq")
    @SequenceGenerator(name = "orders_seq", sequenceName = "orders_id_seq", allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    var id: Long = 0,

    @Column(name = "DATE", nullable = false)
    val date: LocalDate,

    @ManyToOne
    val customer: CustomerEntity,

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val ordersProducts: List<OrdersProductsEntity>,

    @Column(name = "EXTERNAL_ID_FROM_IMPORTED_FILE")
    val externalIdFromImportedFile: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMPORTED_FILE_ID", nullable = false)
    val importedFile: ImportedFileEntity
) {
    constructor(
        date: LocalDate,
        customer: CustomerEntity,
        externalIdFromImportedFile: Int,
        importedFile: ImportedFileEntity
    ) : this(0, date, customer, emptyList(), externalIdFromImportedFile, importedFile)
}