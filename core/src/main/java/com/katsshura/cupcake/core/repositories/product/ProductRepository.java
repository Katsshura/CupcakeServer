package com.katsshura.cupcake.core.repositories.product;

import com.katsshura.cupcake.core.entities.product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
