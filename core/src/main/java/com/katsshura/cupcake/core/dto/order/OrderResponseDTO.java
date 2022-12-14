package com.katsshura.cupcake.core.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("order_items")
    private List<OrderItemResponseDTO> orderItems;

    @JsonProperty("order_total")
    private BigDecimal orderTotal;

    @JsonProperty("delivery_tax")
    private BigDecimal deliveryTax;

    @JsonProperty("order_status")
    private List<OrderStatusDTO> orderStatus;

}
