package com.luizalabs.logistica.desafio.dataprovider.customer

import com.luizalabs.logistica.desafio.core.model.entity.CustomerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository


@Repository
interface CustomerRepository : JpaRepository<CustomerEntity, Long>, JpaSpecificationExecutor<CustomerEntity>