package com.katsshura.cupcake.core.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.katsshura.cupcake.core.enums.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusDTO {

    @JsonProperty("status")
    private OrderStatus status;

    @JsonProperty("active")
    private Boolean active;
}
