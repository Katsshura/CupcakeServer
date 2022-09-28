package com.katsshura.cupcake.core.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true, value = { "popularity" }, allowGetters = true)
public class ProductDTO {

    private Long id;

    @NotBlank
    @JsonProperty("name")
    private String name;

    @NotBlank
    @Size(min = 10)
    @JsonProperty("description")
    private String description;

    @NotNull
    @Positive
    @JsonProperty("price")
    private BigDecimal price;

    @NotBlank
    @JsonProperty("image_url")
    private String imageUrl;

    @NotNull
    @Positive
    @JsonProperty("available_stock")
    private Long availableStock;

    @JsonProperty("popularity")
    private Double popularity;

    public Long getAvailableStock() {
        if (availableStock == null) return 0L;
        return availableStock;
    }

    public Double getPopularity() {
        if (popularity == null) return 0.0;
        return popularity;
    }
}
