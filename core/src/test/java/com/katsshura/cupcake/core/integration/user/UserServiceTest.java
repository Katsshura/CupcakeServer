package com.katsshura.cupcake.core.integration.user;


import com.katsshura.cupcake.core.config.IntegrationTestsConfiguration;
import com.katsshura.cupcake.core.entities.address.AddressEntity;
import com.katsshura.cupcake.core.entities.user.UserEntity;
import com.katsshura.cupcake.core.repositories.user.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@IntegrationTestsConfiguration
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;



    @Nested
    @IntegrationTestsConfiguration
    class CreateUser {

        @Test
        void shouldCreateUserWithSuccess() {
            final var address = AddressEntity.builder()
                    .cep("76804066")
                    .propertyNumber("10")
                    .logradouro("Rua Joaquim Nabuco")
                    .build();
            final var user = UserEntity.builder()
                    .cpf("05155638038")
                    .birthdayDate(LocalDate.parse("1999-01-20"))
                    .email("test1@test.com")
                    .name("Test One")
                    .password("somepassword")
                    .registeredAddresses(List.of(address))
                    .build();

            final var result = userRepository.save(user);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertThat(result.getId(), allOf(greaterThan(0L), notNullValue())),
                    () -> assertEquals(user, result)
            );

        }

        @Test
        void shouldThrowErrorWhenTryingSaveUserInvalidData() {
            final var address = AddressEntity.builder()
                    .cep(null)
                    .propertyNumber("10")
                    .logradouro("Rua Joaquim Nabuco")
                    .build();
            final var user = UserEntity.builder()
                    .cpf(null)
                    .birthdayDate(LocalDate.parse("1999-01-20"))
                    .email(null)
                    .name("Test One")
                    .password("somepassword")
                    .registeredAddresses(List.of(address))
                    .build();

            assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(user));

        }
    }

    @Nested
    @IntegrationTestsConfiguration
    class ListUser {

        void shouldFindUserByEmail() {

        }

        void shouldFindUserByCpf() {

        }

        void shouldFindUserByEmailOrCpf() {

        }

        void shouldValidateUserByEmailOrCpf() {

        }
    }
}
