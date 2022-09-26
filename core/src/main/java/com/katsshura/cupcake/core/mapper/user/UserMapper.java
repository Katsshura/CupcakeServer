package com.katsshura.cupcake.core.mapper.user;

import com.katsshura.cupcake.core.dto.user.UserDTO;
import com.katsshura.cupcake.core.entities.user.UserEntity;
import com.katsshura.cupcake.core.mapper.address.AddressMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {AddressMapper.class})
public interface UserMapper {

    @Mapping(target = "email", source = "source.email")
    @Mapping(target = "name", source = "source.name")
    @Mapping(target = "cpf", source = "source.cpf")
    @Mapping(target = "birthdayDate", source = "source.birthdayDate")
    @Mapping(target = "password", source = "source.password")
    @Mapping(target = "addresses", source = "source.registeredAddresses")
    UserDTO toDTO(UserEntity source);

    @InheritInverseConfiguration(name = "toDTO")
    UserEntity toEntity(UserDTO source);
}
