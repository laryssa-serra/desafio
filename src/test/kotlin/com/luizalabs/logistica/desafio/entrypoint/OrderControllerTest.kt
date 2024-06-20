package com.luizalabs.logistica.desafio.entrypoint

import com.luizalabs.logistica.desafio.core.model.entity.ImportedFileEntity
import com.luizalabs.logistica.desafio.core.service.ImportedFileService
import com.luizalabs.logistica.desafio.core.service.OrderService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(MockitoExtension::class)
@WebMvcTest(OrderController::class)
class OrderControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var orderService: OrderService

    @MockBean
    private lateinit var importedFileService: ImportedFileService

    private lateinit var mockMultipartFile: MockMultipartFile

    @BeforeEach
    fun setUp() {
        val txtContent = listOf(
            "0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308",
            "0000000075                                  Bobbie Batz00000007980000000002     1578.5720211116",
            "0000000049                               Ken Wintheiser00000005230000000003      586.7420210903",
            "0000000014                                 Clelia Hills00000001460000000001      673.4920211125",
            "0000000057                          Elidia Gulgowski IV00000006200000000000     1417.2520210919",
        ).joinToString(separator = "\n")

        mockMultipartFile = MockMultipartFile(
            "file",
            "data_1.txt",
            "text/plain",
            txtContent.toByteArray()
        )
    }

    @Test
    fun `should import orders successfully when file is not found`() {
        Mockito.`when`(importedFileService.findByName(mockMultipartFile.originalFilename)).thenReturn(null)

        mockMvc.perform(
            MockMvcRequestBuilders.multipart("/api/orders/imports").file(mockMultipartFile)
                .contentType(MediaType.MULTIPART_FORM_DATA)
        ).andExpect(MockMvcResultMatchers.status().isCreated)

        Mockito.verify(orderService).importOrders(Mockito.anyString(), Mockito.anyList())
    }

    @Test
    fun `should not import orders when imported file is found`() {
        val importedFile = ImportedFileEntity(name = mockMultipartFile.originalFilename)
        Mockito.`when`(importedFileService.findByName(mockMultipartFile.originalFilename)).thenReturn(importedFile)

        mockMvc.perform(
            MockMvcRequestBuilders.multipart("/api/orders/imports")
                .file(mockMultipartFile)
                .contentType(MediaType.MULTIPART_FORM_DATA)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)

        Mockito.verify(orderService, Mockito.never()).importOrders(Mockito.anyString(), Mockito.anyList())
    }

    @Test
    fun `should return internal server error when exception occurs`() {
        Mockito.`when`(importedFileService.findByName(mockMultipartFile.originalFilename))
            .thenThrow(RuntimeException("Internal Server Error"))

        mockMvc.perform(
            MockMvcRequestBuilders.multipart("/api/orders/imports")
                .file(mockMultipartFile)
                .contentType(MediaType.MULTIPART_FORM_DATA)
        )
            .andExpect(MockMvcResultMatchers.status().isInternalServerError)
    }
}