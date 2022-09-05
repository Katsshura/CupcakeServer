package com.katsshura.cupcake.core.mapper.address;

import com.katsshura.cupcake.core.dto.address.AddressDTO;
import com.katsshura.cupcake.core.entities.address.AddressEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "cep", source = "cep")
    @Mapping(target = "logradouro", source = "logradouro")
    @Mapping(target = "propertyNumber", source = "propertyNumber")
    @Mapping(target = "complement", source = "complement")
    AddressDTO toDTO(AddressEntity source);

    @InheritInverseConfiguration(name = "toDTO")
    AddressEntity toEntity(AddressDTO source);
}
