package com.luizalabs.logistica.desafio.core.service

import com.luizalabs.logistica.desafio.core.model.entity.ProductEntity
import com.luizalabs.logistica.desafio.dataprovider.product.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository
) {

    fun saveAll(products: List<ProductEntity>): List<ProductEntity> {
        return productRepository.saveAll(products)
    }
}