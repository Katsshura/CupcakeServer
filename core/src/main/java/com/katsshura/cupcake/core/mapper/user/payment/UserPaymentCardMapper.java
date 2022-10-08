package com.katsshura.cupcake.core.mapper.user.payment;

import com.katsshura.cupcake.core.dto.user.payment.UserPaymentCardDTO;
import com.katsshura.cupcake.core.entities.user.payment.UserPaymentCardEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserPaymentCardMapper {

    @Mapping(source = "cardNumber", target = "cardNumber")
    @Mapping(source = "expiringDate", target = "expiringDate")
    @Mapping(source = "customName", target = "customName")
    @Mapping(source = "cvv", target = "cvv")
    UserPaymentCardDTO toDTO(UserPaymentCardEntity source);

    @InheritInverseConfiguration(name = "toDTO")
    UserPaymentCardEntity toEntity(UserPaymentCardDTO source);
}
