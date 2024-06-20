package com.luizalabs.logistica.desafio.core

import com.luizalabs.logistica.desafio.core.model.entity.ImportedFileEntity
import com.luizalabs.logistica.desafio.core.service.ImportedFileService
import com.luizalabs.logistica.desafio.dataprovider.importedFile.ImportedFileRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertNull

@ExtendWith(MockitoExtension::class)
class ImportedFileServiceTest {

    @Mock
    private lateinit var importedFileRepository: ImportedFileRepository

    @InjectMocks
    private lateinit var importedFileService: ImportedFileService

    private lateinit var importedFileEntity: ImportedFileEntity

    @BeforeEach
    fun setUp() {
        importedFileEntity = ImportedFileEntity(name = "test_file")
    }

    @Test
    fun `should find file by name`() {
        val fileName = "test_file"
        `when`(importedFileRepository.findByName(fileName)).thenReturn(importedFileEntity)

        val foundFile = importedFileService.findByName(fileName)
        assertEquals(importedFileEntity, foundFile)
    }

    @Test
    fun `should return null when file not found`() {
        val fileName = "non_existent_file"
        `when`(importedFileRepository.findByName(fileName)).thenReturn(null)

        val foundFile = importedFileService.findByName(fileName)
        assertNull(foundFile)
    }

    @Test
    fun `should save file`() {
        val fileName = "new_file"
        val newFileEntity = ImportedFileEntity(name = fileName)
        `when`(importedFileRepository.save(Mockito.any(ImportedFileEntity::class.java))).thenReturn(newFileEntity)

        val savedFile = importedFileService.save(fileName)
        assertEquals(newFileEntity, savedFile)
    }
}