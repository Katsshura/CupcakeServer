package com.katsshura.cupcake.core.integration.product;

import com.katsshura.cupcake.core.config.IntegrationTestsConfiguration;
import com.katsshura.cupcake.core.entities.product.ProductEntity;
import com.katsshura.cupcake.core.repositories.product.ProductRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@IntegrationTestsConfiguration
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Nested
    @IntegrationTestsConfiguration
    class CreateProduct {

        @Test
        void shouldCreateProductWithSuccess() {
            final var product = ProductEntity.builder()
                    .name("Test Product One")
                    .description("Product Integration Test")
                    .price(BigDecimal.valueOf(1.99))
                    .imageUrl("localhost:0000")
                    .availableStock(2L)
                    .popularity(2.0)
                    .build();

            final var result = productRepository.save(product);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertThat(result.getId(), allOf(greaterThan(0L), notNullValue())),
                    () -> assertEquals(product, result)
            );
        }

        @Test
        void shouldThrowErrorWhenTryingSaveProductInvalidData() {
            final var product = ProductEntity.builder()
                    .name(null)
                    .description(null)
                    .price(null)
                    .imageUrl(null)
                    .availableStock(null)
                    .popularity(null)
                    .build();

            assertThrows(DataIntegrityViolationException.class, () -> productRepository.save(product));
        }
    }
}
