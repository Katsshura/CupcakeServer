package com.katsshura.cupcake.core.repositories.user.payment;

import com.katsshura.cupcake.core.entities.user.payment.UserPaymentCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPaymentCardRepository extends JpaRepository<UserPaymentCardEntity, Long> {

    List<UserPaymentCardEntity> findAllByUserId(Long user_id);

}
