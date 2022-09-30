package com.katsshura.cupcake.core.service;

import com.katsshura.cupcake.core.dto.product.ProductDTO;
import com.katsshura.cupcake.core.entities.product.ProductEntity;
import com.katsshura.cupcake.core.exceptions.NotFoundException;
import com.katsshura.cupcake.core.mapper.product.ProductMapper;
import com.katsshura.cupcake.core.repositories.product.ProductRepository;
import com.katsshura.cupcake.core.services.product.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

    @Test
    @DisplayName("Should find product by id and assert valid dto!")
    void shouldFindProductById() {
        final var id = 1L;
        final var product = ProductEntity.builder()
                .popularity(1.0)
                .availableStock(5L)
                .imageUrl("Test")
                .price(BigDecimal.valueOf(2.2))
                .description("Test One For Real")
                .name("Test")
                .build();
        product.setId(id);

        final var productDto = productMapper.toDTO(product);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        final var result = productService.findProductById(id);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(productDto, result),
                () -> verify(productRepository, times(1)).findById(id),
                () -> verify(productMapper, times(2)).toDTO(product)
        );
    }

    @Test
    @DisplayName("Should not find product by id and throw exception!")
    void shouldThrowExceptionWhenProductDoesNotExists() {
        final var id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> productService.findProductById(id));
    }

    @ParameterizedTest(name = "#[{index}] Should find all products and assert valid method call and response!")
    @ValueSource(strings = { "", " ", })
    void shouldGetProductsWithoutFilter(String search) {
        final var pageable = Pageable.unpaged();
        final var list = new PageImpl<>(List.of(
                ProductEntity.builder()
                        .popularity(1.0)
                        .availableStock(5L)
                        .imageUrl("Test")
                        .price(BigDecimal.valueOf(2.2))
                        .description("Test One For Real")
                        .name("Test")
                        .build()
        ));

        when(productRepository.findAllByOrderByPopularityDesc(pageable)).thenReturn(list);

        final var result = productService.getProducts(search, pageable);

        assertAll(
                () -> assertNotNull(result),
                () -> assertFalse(result.isEmpty()),
                () -> verify(productRepository, times(1))
                        .findAllByOrderByPopularityDesc(pageable),
                () -> verify(productMapper, times(1)).toDTO(any())
        );
    }

    @ParameterizedTest(name = "#[{index}] Should find all products with filter and assert valid method call and response!")
    @ValueSource(strings = { "Test", "One" })
    void shouldGetProductsWithFilter(String search) {
        final var pageable = Pageable.unpaged();
        final var list = new PageImpl<>(List.of(
                ProductEntity.builder()
                        .popularity(1.0)
                        .availableStock(5L)
                        .imageUrl("Test")
                        .price(BigDecimal.valueOf(2.2))
                        .description("Test One For Real")
                        .name("Test")
                        .build()
        ));

        when(productRepository
                .findAllByNameContainingIgnoreCaseOrderByPopularityDesc(search, pageable))
                .thenReturn(list);

        final var result = productService.getProducts(search, pageable);

        assertAll(
                () -> assertNotNull(result),
                () -> assertFalse(result.isEmpty()),
                () -> verify(productRepository, times(1))
                        .findAllByNameContainingIgnoreCaseOrderByPopularityDesc(search, pageable),
                () -> verify(productMapper, times(1)).toDTO(any())
        );
    }
}
