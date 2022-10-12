package com.katsshura.cupcake.core.entities.order;

import com.katsshura.cupcake.core.entities.product.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_product")
public class OrderProductEntity {

    @EmbeddedId
    private OrderProductId id;

    @ManyToOne
    @MapsId("orderId")
    private OrderEntity order;
    @ManyToOne
    @MapsId("productId")
    private ProductEntity product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (Objects.equals(getId(), ((OrderProductEntity) o).getId())) return true;
        OrderProductEntity that = (OrderProductEntity) o;
        return Objects.equals(id, that.id)
                && Objects.equals(product, that.product)
                && Objects.equals(quantity, that.quantity)
                && Objects.equals(totalPrice, that.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, product, quantity, totalPrice);
    }
}
