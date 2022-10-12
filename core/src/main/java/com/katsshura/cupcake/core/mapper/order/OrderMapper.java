package com.katsshura.cupcake.core.mapper.order;

import com.katsshura.cupcake.core.dto.order.OrderItemResponseDTO;
import com.katsshura.cupcake.core.dto.order.OrderResponseDTO;
import com.katsshura.cupcake.core.dto.order.OrderStatusDTO;
import com.katsshura.cupcake.core.entities.order.OrderEntity;
import com.katsshura.cupcake.core.entities.order.OrderProductEntity;
import com.katsshura.cupcake.core.entities.order.OrderStatusEntity;
import com.katsshura.cupcake.core.entities.product.ProductEntity;
import com.katsshura.cupcake.core.mapper.product.ProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = { ProductMapper.class })
public interface OrderMapper {

    BigDecimal DELIVERY_TAX = new BigDecimal("6.99");

    @Mapping(source = "product", target = "product")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "totalPrice", target = "total")
    OrderItemResponseDTO toOrderItemDto(OrderProductEntity source);

    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "productEntity", target = "orderItems")
    @Mapping(source = "order.status", target = "orderStatus")
    @Mapping(source = "productEntity", target = "orderTotal")
    @Mapping(target = "deliveryTax", constant = "6.99")
    OrderResponseDTO toOrderResponseDto(OrderEntity order,
                                        List<OrderProductEntity> productEntity);

    @Mapping(source = "status", target = "status")
    @Mapping(source = "active", target = "active")
    OrderStatusDTO toOrderStatusEntity(OrderStatusEntity orderStatusEntity);

    default ProductEntity toProductEntityFromOrderProduct(OrderProductEntity orderProductEntity) {
        return orderProductEntity.getProduct();
    }

    default BigDecimal mapOrderTotal(List<OrderProductEntity> productEntities) {
        BigDecimal total = new BigDecimal(0);

        for (var productEntity : productEntities) {
            total = total.add(productEntity.getTotalPrice());
        }

        return total.add(DELIVERY_TAX);
    }
}
