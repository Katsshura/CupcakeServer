package com.katsshura.cupcake.core.services.product;

import com.katsshura.cupcake.core.dto.product.ProductDTO;
import com.katsshura.cupcake.core.exceptions.NotFoundException;
import com.katsshura.cupcake.core.mapper.product.ProductMapper;
import com.katsshura.cupcake.core.repositories.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public ProductDTO findProductById(final Long id) {
        log.debug("Fetching product with id: [{}]", id);

        final var result = this.productRepository.findById(id);

        if (result.isEmpty()) {
            log.debug("Product not found for id: [{}]", id);
            throw new NotFoundException("product_id: " + id);
        }

        log.debug("Product with id: [{}] was found: [{}]", id, result.get());

        return this.productMapper.toDTO(result.get());
    }

    public Page<ProductDTO> getProducts(final String search, final Pageable pageable) {
        log.debug("Fetching products with search parameter: {}", search);

        if (StringUtils.isEmpty(search) || StringUtils.isBlank(search)) {
            final var result =  this
                    .productRepository.findAllByOrderByPopularityDesc(pageable);
            return result.map(this.productMapper::toDTO);
        }

        final var result = this.productRepository
                .findAllByNameContainingIgnoreCaseOrderByPopularityDesc(search, pageable);
        return result.map(this.productMapper::toDTO);
    }
}
