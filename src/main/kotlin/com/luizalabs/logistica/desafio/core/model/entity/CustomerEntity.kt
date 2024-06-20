package com.luizalabs.logistica.desafio.core.model.entity

import jakarta.persistence.*

@Entity
@Table(name = "CUSTOMERS")
data class CustomerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customers_seq")
    @SequenceGenerator(name = "customers_seq", sequenceName = "customers_id_seq", allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    var id: Long = 0,

    @Column(name = "NAME")
    val name: String = "",

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var orders: List<OrderEntity>,

    @Column(name = "EXTERNAL_ID_FROM_IMPORTED_FILE")
    val externalIdFromImportedFile: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMPORTED_FILE_ID", nullable = false)
    val importedFile: ImportedFileEntity?
) {
    constructor(
        name: String,
        externalIdFromImportedFile: Int,
        importedFile: ImportedFileEntity?
    ) : this(0, name, emptyList(), externalIdFromImportedFile, importedFile)
}