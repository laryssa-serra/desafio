package com.luizalabs.logistica.desafio.dataprovider.customer

import com.luizalabs.logistica.desafio.core.model.entity.CustomerEntity
import com.luizalabs.logistica.desafio.core.model.entity.OrderEntity
import jakarta.persistence.criteria.JoinType
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDate

class CustomerSpecification {
    companion object {
        fun hasOrders(dateFrom: LocalDate?, dateTo: LocalDate?, orderId: Long?): Specification<CustomerEntity> {
            return Specification { root, query, criteriaBuilder ->
                val orders = root.join<CustomerEntity, OrderEntity>("orders", JoinType.INNER)
                val predicates = mutableListOf<jakarta.persistence.criteria.Predicate>()

                dateFrom?.let {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(orders.get("date"), it))
                }
                dateTo?.let {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(orders.get("date"), it))
                }
                orderId?.let {
                    predicates.add(criteriaBuilder.equal(orders.get<Long>("id"), it))
                }

                // Add sorting
                query.orderBy(criteriaBuilder.asc(root.get<String>("name")))

                criteriaBuilder.and(*predicates.toTypedArray())

            }
        }
    }
}