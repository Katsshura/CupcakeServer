package com.katsshura.cupcake.core.entities.order;

import com.katsshura.cupcake.core.entities.BaseEntity;
import com.katsshura.cupcake.core.entities.user.UserEntity;
import com.katsshura.cupcake.core.entities.user.payment.UserPaymentCardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`order`")
public class OrderEntity extends BaseEntity  {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "BIGINT")
    private UserEntity user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_payment_card_id", nullable = false, columnDefinition = "BIGINT")
    private UserPaymentCardEntity userPaymentId;

    @OneToMany(mappedBy = "order")
    private List<OrderStatusEntity> status;

    @OneToMany(mappedBy = "order")
    private List<OrderProductEntity> products;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (Objects.equals(getId(), ((OrderEntity) o).getId())) return true;
        OrderEntity that = (OrderEntity) o;
        return Objects.equals(userPaymentId, that.userPaymentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userPaymentId);
    }
}
