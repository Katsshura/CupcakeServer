package com.katsshura.cupcake.core.mapper.product;

import com.katsshura.cupcake.core.dto.product.ProductDTO;
import com.katsshura.cupcake.core.entities.product.ProductEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "imageUrl", source = "imageUrl")
    @Mapping(target = "availableStock", source = "availableStock")
    @Mapping(target = "popularity", source = "popularity")
    ProductDTO toDTO(ProductEntity source);

    @InheritInverseConfiguration(name = "toDTO")
    ProductEntity toEntity(ProductDTO source);
}
