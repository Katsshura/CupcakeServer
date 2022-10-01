package com.katsshura.cupcake.core.integration.product;

import com.katsshura.cupcake.core.config.IntegrationTestsConfiguration;
import com.katsshura.cupcake.core.entities.product.ProductEntity;
import com.katsshura.cupcake.core.repositories.product.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.math.BigDecimal;
import java.util.Comparator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Nested
    @IntegrationTestsConfiguration
    class ListProduct {
        @ParameterizedTest(name = "#[{index}] Should assertNotNull and assertEquals for all entity properties" +
                "with given parameters!")
        @CsvSource(value = { "'',5", "one, 1", "two, 1", "three,1", "four,1", "five,1", "test,5" })
        @SqlGroup({
                @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                        scripts = "classpath:scripts/product/BeforeProductRepositoryTest.sql"),
                @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                        scripts = "classpath:scripts/product/AfterProductRepositoryTest.sql")
        })
        void shouldFindAllWithFiltersSortedByPopularity(String search, int expectedResult) {
            final var pageable = Pageable.unpaged();
            final var result = productRepository
                    .findAllByNameContainingIgnoreCaseOrderByPopularityDesc(search, pageable);
            final var maxPopularity = result.stream()
                    .max(Comparator.comparingDouble(ProductEntity::getPopularity)).get()
                    .getPopularity();

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertFalse(result.isEmpty()),
                    () -> assertEquals(maxPopularity, result.getContent().get(0).getPopularity())
            );
        }

        @Test
        @DisplayName("Should find all products sorted by popularity value!")
        @SqlGroup({
                @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                        scripts = "classpath:scripts/product/BeforeProductRepositoryTest.sql"),
                @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                        scripts = "classpath:scripts/product/AfterProductRepositoryTest.sql")
        })
        void shouldFindAllSortedByPopularity() {
            final var pageable = Pageable.unpaged();
            final var result = productRepository
                    .findAllByOrderByPopularityDesc(pageable);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertFalse(result.isEmpty()),
                    () -> assertEquals(5.0, result.getContent().get(0).getPopularity()),
                    () -> assertEquals(4.0, result.getContent().get(1).getPopularity()),
                    () -> assertEquals(3.0, result.getContent().get(2).getPopularity()),
                    () -> assertEquals(2.0, result.getContent().get(3).getPopularity()),
                    () -> assertEquals(1.0, result.getContent().get(4).getPopularity())
            );
        }
    }

}
