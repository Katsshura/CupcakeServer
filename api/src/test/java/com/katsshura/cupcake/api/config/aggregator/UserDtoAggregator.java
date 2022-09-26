package com.katsshura.cupcake.api.config.aggregator;

import com.katsshura.cupcake.core.dto.address.AddressDTO;
import com.katsshura.cupcake.core.dto.user.UserDTO;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

import java.time.LocalDate;
import java.util.List;

public class UserDtoAggregator implements ArgumentsAggregator {
    @Override
    public Object aggregateArguments(final ArgumentsAccessor accessor,
                                     final ParameterContext context) throws ArgumentsAggregationException {

        final var addressDto = AddressDTO.builder()
                .cep(accessor.getString(5))
                .logradouro(accessor.getString(6))
                .propertyNumber(accessor.getString(7))
                .complement(accessor.getString(8))
                .build();

        return UserDTO.builder()
                .name(accessor.getString(0))
                .email(accessor.getString(1))
                .cpf(accessor.getString(2))
                .birthdayDate(accessor.getString(3) == null ? null : LocalDate.parse(accessor.getString(3)))
                .password(accessor.getString(4))
                .addresses(List.of(addressDto))
                .build();
    }
}
