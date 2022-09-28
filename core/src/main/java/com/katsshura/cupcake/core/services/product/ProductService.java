package com.katsshura.cupcake.core.services.product;

import com.katsshura.cupcake.core.dto.product.ProductDTO;
import com.katsshura.cupcake.core.mapper.product.ProductMapper;
import com.katsshura.cupcake.core.repositories.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductService(final ProductRepository productRepository,
                          final ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductDTO createProduct(final ProductDTO productDTO) {
        log.debug("Persisting prodcut: [{}]", productDTO);

        final var result = this.productRepository
                .save(this.productMapper.toEntity(productDTO));

        log.debug("Product persisted with success! [{}]", result);

        return this.productMapper.toDTO(result);
    }
}
