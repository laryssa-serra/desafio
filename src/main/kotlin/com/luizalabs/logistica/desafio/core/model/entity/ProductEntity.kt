package com.luizalabs.logistica.desafio.core.model.entity

import jakarta.persistence.*

@Entity
@Table(name = "PRODUCTS")
data class ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_seq")
    @SequenceGenerator(name = "products_seq", sequenceName = "products_id_seq", allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    val id: Long = 0,

    @Column(name = "EXTERNAL_ID_FROM_IMPORTED_FILE")
    val externalIdFromImportedFile: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMPORTED_FILE_ID", nullable = false)
    val importedFile: ImportedFileEntity
)
