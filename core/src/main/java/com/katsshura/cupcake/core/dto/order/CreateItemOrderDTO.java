package com.katsshura.cupcake.core.dto.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateItemOrderDTO {

    @NotNull
    @Positive
    @JsonProperty("product_id")
    private Long productId;

    @NotNull
    @Positive
    @JsonProperty("quantity")
    private Integer quantity;
}
