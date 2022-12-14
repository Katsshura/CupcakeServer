package com.katsshura.cupcake.core.repositories.order;

import com.katsshura.cupcake.core.entities.order.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    Page<OrderEntity> findAllByUserId(Long userId, Pageable pageable);
}
