package com.katsshura.cupcake.core.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.katsshura.cupcake.core.dto.product.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDTO {

    @JsonProperty("product")
    private ProductDTO product;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("total")
    private BigDecimal total;
}
