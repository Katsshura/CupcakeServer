package com.katsshura.cupcake.core.repositories.order;

import com.katsshura.cupcake.core.entities.order.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Long> {
}
