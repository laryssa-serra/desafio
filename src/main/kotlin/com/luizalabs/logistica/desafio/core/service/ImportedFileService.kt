package com.luizalabs.logistica.desafio.core.service

import com.luizalabs.logistica.desafio.core.model.entity.ImportedFileEntity
import com.luizalabs.logistica.desafio.dataprovider.importedFile.ImportedFileRepository
import org.springframework.stereotype.Service

@Service
class ImportedFileService(
    private val importedFileRepository: ImportedFileRepository
) {

    fun findByName(name: String): ImportedFileEntity? {
        return importedFileRepository.findByName(name)
    }

    fun save(fileName: String): ImportedFileEntity {
        return importedFileRepository.save(ImportedFileEntity(name = fileName))
    }
}
