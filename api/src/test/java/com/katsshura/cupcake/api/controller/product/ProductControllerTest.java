package com.katsshura.cupcake.api.controller.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.katsshura.cupcake.api.config.ControllerTestsConfiguration;
import com.katsshura.cupcake.api.config.aggregator.ProductDtoAggregator;
import com.katsshura.cupcake.core.dto.product.ProductDTO;
import com.katsshura.cupcake.core.exceptions.NotFoundException;
import com.katsshura.cupcake.core.services.product.ProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTestsConfiguration
public class ProductControllerTest {

    private static final String REQUEST_URL = "/product";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @BeforeAll
    static void setup() {
        MAPPER.registerModule(new JavaTimeModule());
        MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @ParameterizedTest(name = "#[{index}] Should assert status code 201 for valid product!")
    @CsvFileSource(resources = "/csv/product/ProductInputValidData.csv", numLinesToSkip = 1)
    void shouldCreateProduct(@AggregateWith(ProductDtoAggregator.class)ProductDTO productDTO) throws Exception {
        when(productService.createProduct(productDTO)).thenReturn(productDTO);

        mockMvc.perform(post(REQUEST_URL)
                .content(MAPPER.writeValueAsString(productDTO))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isCreated());
    }

    @ParameterizedTest(name = "#[{index}] Should assert status code 400 for invalid product!")
    @CsvFileSource(resources = "/csv/product/ProductInputInvalidData.csv", numLinesToSkip = 1)
    void shouldNotCreateProduct(@AggregateWith(ProductDtoAggregator.class)ProductDTO productDTO) throws Exception {
        mockMvc.perform(post(REQUEST_URL)
                .content(MAPPER.writeValueAsString(productDTO))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isBadRequest());
    }

    @ParameterizedTest(name = "#[{index}] Should find all products and return status 200!")
    @ValueSource(strings = { "", " ", "One", "test"})
    void shouldGetAllProducts(String search) throws Exception {
        final var product = ProductDTO.builder()
                .name("Test Product One")
                .description("Product Integration Test")
                .price(BigDecimal.valueOf(1.99))
                .imageUrl("localhost:0000")
                .availableStock(2L)
                .popularity(2.0)
                .build();
        final var pageable = Pageable.unpaged();
        final var mock = new PageImpl<>(List.of(product));

        when(productService.getProducts(search, pageable)).thenReturn(mock);

        mockMvc.perform(get(REQUEST_URL)).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return status 200 ok for existing product id")
    void shouldFindProductById() throws Exception {
        final var id = 1L;
        final var product = ProductDTO.builder()
                .name("Test Product One")
                .description("Product Integration Test")
                .price(BigDecimal.valueOf(1.99))
                .imageUrl("localhost:0000")
                .availableStock(2L)
                .popularity(2.0)
                .build();
        product.setId(id);
        when(productService.findProductById(id)).thenReturn(product);

        mockMvc.perform(get(REQUEST_URL + "/" + id)).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return status 404 not found for non existing product id")
    void shouldNotFindProductById() throws Exception {
        final var id = 2L;
        when(productService.findProductById(id)).thenThrow(new NotFoundException("Test not found"));

        mockMvc.perform(get(REQUEST_URL + "/" + id)).andExpect(status().isNotFound());
    }
}
