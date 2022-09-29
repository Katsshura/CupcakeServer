package com.katsshura.cupcake.api.controller.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.katsshura.cupcake.api.config.ControllerTestsConfiguration;
import com.katsshura.cupcake.api.config.aggregator.ProductDtoAggregator;
import com.katsshura.cupcake.core.dto.product.ProductDTO;
import com.katsshura.cupcake.core.services.product.ProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
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
}
