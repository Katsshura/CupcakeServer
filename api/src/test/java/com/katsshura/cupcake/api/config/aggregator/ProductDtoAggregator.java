package com.katsshura.cupcake.api.config.aggregator;

import com.katsshura.cupcake.core.dto.product.ProductDTO;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

import java.math.BigDecimal;

public class ProductDtoAggregator implements ArgumentsAggregator {
    @Override
    public Object aggregateArguments(final ArgumentsAccessor accessor,
                                     final ParameterContext context) throws ArgumentsAggregationException {


        return ProductDTO.builder()
                .name(accessor.getString(0))
                .description(accessor.getString(1))
                .price(accessor.getString(2) == null ? null : new BigDecimal(accessor.getString(2)))
                .imageUrl(accessor.getString(3))
                .availableStock(accessor.getString(4) == null ? null : Long.parseLong(accessor.getString(4)))
                .build();
    }
}
