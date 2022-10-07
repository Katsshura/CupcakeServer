package com.katsshura.cupcake.core.integration.highlight;

import com.katsshura.cupcake.core.config.IntegrationTestsConfiguration;
import com.katsshura.cupcake.core.entities.highlight.HighlightEntity;
import com.katsshura.cupcake.core.entities.product.ProductEntity;
import com.katsshura.cupcake.core.repositories.highlight.HighlightRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IntegrationTestsConfiguration
public class HighlightRepositoryTest {

    @Autowired
    private HighlightRepository highlightRepository;

    @Nested
    @IntegrationTestsConfiguration
    class CreateHighlight {
        @Test
        @Transactional
        @SqlGroup({
                @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                        scripts = "classpath:scripts/product/BeforeProductRepositoryTest.sql"),
                @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                        scripts = "classpath:scripts/product/AfterProductRepositoryTest.sql")
        })
        void shouldSaveHighlight() {
            final var productEntity = ProductEntity.builder().build();
            productEntity.setId(10L);

            final var highlightEntity = HighlightEntity.builder()
                    .title("Test")
                    .subtitle("Test SubTitle")
                    .product_id(10L)
                    .build();

            final var result = highlightRepository.save(highlightEntity);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertThat(result.getId(), allOf(greaterThan(0L), notNullValue())),
                    () -> assertEquals(highlightEntity, result)
            );
        }
    }

    @Nested
    @IntegrationTestsConfiguration
    class ListHighlight {
        @Test
        @Transactional
        @SqlGroup({
                @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                        scripts = "classpath:scripts/highlight/BeforeHighlightRepositoryTest.sql"),
                @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                        scripts = "classpath:scripts/highlight/AfterHighlightRepositoryTest.sql")
        })
        void shouldFindEnabled() {
            final var result = highlightRepository.findFirstByEnabledIsTrue();

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertFalse(result.isEmpty()),
                    () -> assertEquals(20, result.get().getId()),
                    () -> assertEquals("Test Two", result.get().getTitle()),
                    () -> assertEquals("Test Description Two", result.get().getSubtitle()),
                    () -> assertEquals(20, result.get().getProduct_id()),
                    () -> assertTrue(result.get().getEnabled())
            );
        }
    }
}
