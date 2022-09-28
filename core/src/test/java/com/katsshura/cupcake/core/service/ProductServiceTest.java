package com.katsshura.cupcake.core.service;

import com.katsshura.cupcake.core.dto.product.ProductDTO;
import com.katsshura.cupcake.core.mapper.product.ProductMapper;
import com.katsshura.cupcake.core.repositories.product.ProductRepository;
import com.katsshura.cupcake.core.services.product.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Spy
    private static ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void contextLoads() {
        assertAll(
                () -> assertNotNull(productMapper),
                () -> assertNotNull(productRepository)
        );
    }

    @Test
    @DisplayName("Should create product with valid data and return valid DTO")
    void shouldCreateProduct() {
        final var product = ProductDTO.builder()
                .name("Test Product One")
                .description("Product Integration Test")
                .price(BigDecimal.valueOf(1.99))
                .imageUrl("localhost:0000")
                .availableStock(2L)
                .popularity(2.0)
                .build();

        final var productEntity = productMapper.toEntity(product);
        productEntity.setId(1L);

        when(productRepository.save(any())).thenReturn(productEntity);

        final var result = productService.createProduct(product);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(productEntity.getId(), result.getId()),
                () -> verify(productRepository, times(1)).save(any()),
                () -> verify(productMapper, times(2)).toEntity(product),
                () -> verify(productMapper, times(1)).toDTO(productEntity)
        );

    }
}
