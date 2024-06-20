package com.luizalabs.logistica.desafio.entrypoint

import com.luizalabs.logistica.desafio.core.model.dto.toOrderImportRequestDto
import com.luizalabs.logistica.desafio.core.service.ImportedFileService
import com.luizalabs.logistica.desafio.core.service.OrderService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.InputStreamReader

@RestController
@RequestMapping("/api")
class OrderController(
    private val orderService: OrderService,
    private val importedFileService: ImportedFileService
) {
    private val logger = LoggerFactory.getLogger(OrderController::class.java)

    @PostMapping("/orders/imports")
    fun importOrdersFromMultipartFile(@RequestParam("file") multipartFile: MultipartFile): ResponseEntity<Any> {
        logger.info("Receiving ${multipartFile.originalFilename} file to import orders")

        return try {
            val importedFile = importedFileService.findByName(multipartFile.originalFilename.toString())

            if (importedFile == null) {
                val lines = getMultipartFileLines(multipartFile)
                val orderImportRequestsDto = lines.map { it.toOrderImportRequestDto() }
                orderService.importOrders(multipartFile.originalFilename.toString(), orderImportRequestsDto)
            }

            ResponseEntity(HttpStatus.CREATED)
        } catch (e: Exception) {
            logger.error("Error importing orders from file ${multipartFile.originalFilename}", e)
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    private fun getMultipartFileLines(multipartFile: MultipartFile): List<String> {
        val lines = ArrayList<String>()
        BufferedReader(InputStreamReader(multipartFile.inputStream)).use { reader ->
            reader.forEachLine { line ->
                lines.add(line)
            }
        }
        return lines
    }
}