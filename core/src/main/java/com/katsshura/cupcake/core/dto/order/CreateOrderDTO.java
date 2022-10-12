package com.katsshura.cupcake.core.dto.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateOrderDTO {

    @NotNull
    @NotEmpty
    @JsonProperty("products_quantities")
    @Valid
    private List<CreateItemOrderDTO> productIdQuantity;

    @NotNull
    @Positive
    @JsonProperty("user_id")
    private Long userId;

    @NotNull
    @Positive
    @JsonProperty("payment_id")
    private Long paymentId;

    @NotNull
    @PositiveOrZero
    @JsonProperty("extra_charges")
    private Long extraCharges;
}
