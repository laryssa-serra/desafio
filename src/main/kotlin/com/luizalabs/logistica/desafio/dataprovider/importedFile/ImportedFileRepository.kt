package com.luizalabs.logistica.desafio.dataprovider.importedFile

import com.luizalabs.logistica.desafio.core.model.entity.ImportedFileEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ImportedFileRepository : JpaRepository<ImportedFileEntity, Long>{
    fun findByName(name: String): ImportedFileEntity?
}