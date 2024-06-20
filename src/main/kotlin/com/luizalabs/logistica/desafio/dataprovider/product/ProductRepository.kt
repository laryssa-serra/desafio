package com.luizalabs.logistica.desafio.dataprovider.product

import com.luizalabs.logistica.desafio.core.model.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<ProductEntity, Long>