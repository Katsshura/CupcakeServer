package com.katsshura.cupcake.core.integration.highlight;

import com.katsshura.cupcake.core.config.IntegrationTestsConfiguration;
import com.katsshura.cupcake.core.entities.highlight.HighlightEntity;
import com.katsshura.cupcake.core.entities.product.ProductEntity;
import com.katsshura.cupcake.core.repositories.highlight.HighlightRepository;
import com.katsshura.cupcake.core.repositories.product.ProductRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@IntegrationTestsConfiguration
public class HighlightRepositoryTest {

    @Autowired
    private HighlightRepository highlightRepository;

    @Autowired
    private ProductRepository productRepository;

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

    class ListHighlight {

    }
}
