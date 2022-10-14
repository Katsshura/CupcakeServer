package com.katsshura.cupcake.core.entities.user.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.katsshura.cupcake.core.entities.BaseEntity;
import com.katsshura.cupcake.core.entities.user.UserEntity;
import com.katsshura.cupcake.core.enums.card.CardFlag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_payment_card")
public class UserPaymentCardEntity extends BaseEntity {

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "expiring_date")
    private String expiringDate;

    @Column(name = "cvv")
    private Integer cvv;

    @Column(name = "custom_name")
    private String customName;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_flag", nullable = false)
    private CardFlag cardFlag;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (Objects.equals(getId(), ((UserPaymentCardEntity) o).getId())) return true;
        UserPaymentCardEntity that = (UserPaymentCardEntity) o;
        return Objects.equals(cardNumber, that.cardNumber)
                && Objects.equals(expiringDate, that.expiringDate)
                && Objects.equals(cvv, that.cvv)
                && Objects.equals(customName, that.customName)
                && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cardNumber, expiringDate, cvv, customName, user);
    }
}
