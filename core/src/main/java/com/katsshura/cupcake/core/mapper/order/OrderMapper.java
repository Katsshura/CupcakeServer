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

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = { ProductMapper.class })
public interface OrderMapper {

    @Mapping(source = "product", target = "product")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "totalPrice", target = "total")
    OrderItemResponseDTO toOrderItemDto(OrderProductEntity source);

    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "productEntity", target = "orderItems")
    @Mapping(source = "order.status", target = "orderStatus")
    OrderResponseDTO toOrderResponseDto(OrderEntity order,
                                        List<OrderProductEntity> productEntity);

    @Mapping(source = "status", target = "status")
    @Mapping(source = "active", target = "active")
    OrderStatusDTO toOrderStatusEntity(OrderStatusEntity orderStatusEntity);

    default ProductEntity toProductEntityFromOrderProduct(OrderProductEntity orderProductEntity) {
        return orderProductEntity.getProduct();
    }
}
