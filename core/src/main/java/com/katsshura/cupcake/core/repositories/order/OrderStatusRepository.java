package com.katsshura.cupcake.core.repositories.order;

import com.katsshura.cupcake.core.entities.order.OrderStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatusEntity, Long> {
}
