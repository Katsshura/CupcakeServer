package com.katsshura.cupcake.core.repositories.product;

import com.katsshura.cupcake.core.entities.product.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Page<ProductEntity> findAllByNameContainingIgnoreCaseOrderByPopularityDesc(String searchName, Pageable pageable);

    Page<ProductEntity> findAllByOrderByPopularityDesc(Pageable pageable);
}
