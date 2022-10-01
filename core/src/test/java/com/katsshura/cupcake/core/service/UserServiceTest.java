package com.katsshura.cupcake.core.service;

import com.katsshura.cupcake.core.dto.address.AddressDTO;
import com.katsshura.cupcake.core.dto.user.UserDTO;
import com.katsshura.cupcake.core.exceptions.AlreadyExistsException;
import com.katsshura.cupcake.core.mapper.address.AddressMapper;
import com.katsshura.cupcake.core.mapper.user.UserMapper;
import com.katsshura.cupcake.core.repositories.user.UserRepository;
import com.katsshura.cupcake.core.services.user.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Spy
    private static UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeAll
    static void setup() {
        final var addressMapper = Mappers.getMapper(AddressMapper.class);
        ReflectionTestUtils.setField(userMapper, "addressMapper", addressMapper);
    }

    @Test
    @DisplayName("Should persist user with valid data and return valid DTO")
    void shouldPersistUser() {
        final var address = AddressDTO.builder()
                .cep("76804066").propertyNumber("10")
                .logradouro("Rua Joaquim Nabuco").build();

        final var inputUser = UserDTO.builder().cpf("05155638038")
                .birthdayDate(LocalDate.parse("1999-01-20"))
                .email("test1@test.com")
                .name("Test One")
                .password("somepassword")
                .addresses(List.of(address))
                .build();

        final var userEntity = userMapper.toEntity(inputUser);
        userEntity.setId(1L);
        userEntity.getRegisteredAddresses().get(0).setId(2L);

        when(userRepository.existsByEmailOrCpf(inputUser.getEmail(), inputUser.getCpf())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(userEntity);


        final var result = userService.persistUser(inputUser);

        assertAll(
                () -> assertNotNull(result),
                () -> verify(userRepository, times(1))
                        .existsByEmailOrCpf(inputUser.getEmail(), inputUser.getCpf()),
                () -> verify(userRepository, times(1)).save(any()),
                () -> verify(userMapper, times(2)).toEntity(inputUser),
                () -> verify(userMapper, times(1)).toDTO(userEntity)
        );
    }

    @Test
    @DisplayName("Should throw exception for already existing user on database")
    void shouldThrowException() {
        final var address = AddressDTO.builder()
                .cep("76804066").propertyNumber("10")
                .logradouro("Rua Joaquim Nabuco").build();

        final var inputUser = UserDTO.builder().cpf("05155638038")
                .birthdayDate(LocalDate.parse("1999-01-20"))
                .email("test1@test.com")
                .name("Test One")
                .password("somepassword")
                .addresses(List.of(address))
                .build();

        when(userRepository.existsByEmailOrCpf(inputUser.getEmail(), inputUser.getCpf())).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> userService.persistUser(inputUser));
    }
}
