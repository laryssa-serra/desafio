package com.luizalabs.logistica.desafio.entrypoint

import com.luizalabs.logistica.desafio.core.model.dto.CustomerResponseDto
import com.luizalabs.logistica.desafio.core.model.dto.toResponse
import com.luizalabs.logistica.desafio.core.service.CustomerService
import org.slf4j.LoggerFactory
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("api")
class CustomerController(
    private val customersService: CustomerService
) {

    private val logger = LoggerFactory.getLogger(CustomerController::class.java)

    @GetMapping("customers")
    fun getAllCustomersResponse(
        @RequestParam("date_from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) dateFrom: LocalDate?,
        @RequestParam("date_to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) dateTo: LocalDate?,
        @RequestParam("order_id", required = false) orderId: Long?
    ): ResponseEntity<List<CustomerResponseDto>> {
        return try {
            val customersResponse = customersService.findCustomersByOrdersParams(dateFrom, dateTo, orderId).map { customer -> customer.toResponse() }
            ResponseEntity(customersResponse, HttpStatus.OK)
        } catch (e: Exception) {
            logger.error("Erro ao buscar resposta de todos os clientes", e)
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}