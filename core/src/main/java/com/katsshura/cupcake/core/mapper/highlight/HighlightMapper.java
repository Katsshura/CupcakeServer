package com.katsshura.cupcake.core.mapper.highlight;

import com.katsshura.cupcake.core.dto.highlight.HighlightDTO;
import com.katsshura.cupcake.core.entities.highlight.HighlightEntity;
import com.katsshura.cupcake.core.entities.product.ProductEntity;
import com.katsshura.cupcake.core.mapper.product.ProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = { ProductMapper.class })
public interface HighlightMapper {

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "entity.title", target = "title")
    @Mapping(source = "entity.subtitle", target = "subtitle")
    @Mapping(source = "entity.enabled", target = "enabled")
    @Mapping(source = "product", target = "product")
    HighlightDTO toDTO(HighlightEntity entity, ProductEntity product);

    @Mapping(source = "source.title", target = "title")
    @Mapping(source = "source.subtitle", target = "subtitle")
    @Mapping(source = "source.enabled", target = "enabled")
    @Mapping(source = "product.id", target = "product_id")
    HighlightEntity toEntity(HighlightDTO source);
}
