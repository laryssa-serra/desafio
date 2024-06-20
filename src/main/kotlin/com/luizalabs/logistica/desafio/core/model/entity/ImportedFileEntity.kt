package com.luizalabs.logistica.desafio.core.model.entity

import jakarta.persistence.*

@Entity
@Table(name = "IMPORTED_FILES")
data class ImportedFileEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "imported_files_seq")
    @SequenceGenerator(name = "imported_files_seq", sequenceName = "imported_files_id_seq", allocationSize = 1)
    @Column(name = "ID")
    val id: Long = 0,

    val name: String = ""
)