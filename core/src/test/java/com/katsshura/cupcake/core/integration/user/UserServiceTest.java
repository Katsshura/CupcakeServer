package com.katsshura.cupcake.core.integration.user;


import com.katsshura.cupcake.core.config.IntegrationTestsConfiguration;
import com.katsshura.cupcake.core.entities.address.AddressEntity;
import com.katsshura.cupcake.core.entities.user.UserEntity;
import com.katsshura.cupcake.core.repositories.user.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import javax.transaction.Transactional;
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
        @ParameterizedTest(name = "#[{index}] Should assertNotNull and assertEquals for all entity properties when" +
                " find by email with parameters values: Email = {0} | Name = {1} | CPF = {2} | Password = {3} | CEP = {4}")
        @CsvFileSource(resources = "/csv/user/UserRepositoryValidData.csv", numLinesToSkip = 1)
        @SqlGroup({
                @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                        scripts = "classpath:scripts/user/BeforeUserRepositoryTest.sql"),
                @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                        scripts = "classpath:scripts/user/AfterUserRepositoryTest.sql")
        })
        @Transactional
        void shouldFindUserByEmail(final String name, final String email, final String cpf,
                                   final String password, final String cep) {

            final var result = userRepository.findByEmail(email);

            assertAll(
                    () -> assertFalse(result.isEmpty()),
                    () -> assertEquals(email, result.get().getEmail()),
                    () -> assertEquals(name, result.get().getName()),
                    () -> assertEquals(cpf, result.get().getCpf()),
                    () -> assertEquals(password, result.get().getPassword()),
                    () -> assertEquals(cep, result.get().getRegisteredAddresses().get(0).getCep())
            );
        }

        @ParameterizedTest(name = "#[{index}] Should assertNotNull and assertEquals for all entity properties when" +
                " find by cpf with parameters values: Email = {0} | Name = {1} | CPF = {2} | Password = {3} | CEP = {4}")
        @CsvFileSource(resources = "/csv/user/UserRepositoryValidData.csv", numLinesToSkip = 1)
        @SqlGroup({
                @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                        scripts = "classpath:scripts/user/BeforeUserRepositoryTest.sql"),
                @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                        scripts = "classpath:scripts/user/AfterUserRepositoryTest.sql")
        })
        @Transactional
        void shouldFindUserByCpf(final String name, final String email, final String cpf,
                                 final String password, final String cep) {

            final var result = userRepository.findByCpf(cpf);

            assertAll(
                    () -> assertFalse(result.isEmpty()),
                    () -> assertEquals(email, result.get().getEmail()),
                    () -> assertEquals(name, result.get().getName()),
                    () -> assertEquals(cpf, result.get().getCpf()),
                    () -> assertEquals(password, result.get().getPassword()),
                    () -> assertEquals(cep, result.get().getRegisteredAddresses().get(0).getCep())
            );
        }

        @ParameterizedTest(name = "#[{index}] Should assertNotNull and assertEquals for all entity properties when" +
                " find by email or cpf with parameters values: Email = {0} | Name = {1} | CPF = {2} | Password = {3} | CEP = {4}")
        @CsvFileSource(resources = "/csv/user/UserRepositoryValidData.csv", numLinesToSkip = 1)
        @SqlGroup({
                @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                        scripts = "classpath:scripts/user/BeforeUserRepositoryTest.sql"),
                @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                        scripts = "classpath:scripts/user/AfterUserRepositoryTest.sql")
        })
        @Transactional
        void shouldFindUserByEmailOrCpf(final String name, final String email, final String cpf,
                                        final String password, final String cep) {

            final var result = userRepository.findByEmailOrCpf(email, cpf);

            assertAll(
                    () -> assertFalse(result.isEmpty()),
                    () -> assertEquals(email, result.get().getEmail()),
                    () -> assertEquals(name, result.get().getName()),
                    () -> assertEquals(cpf, result.get().getCpf()),
                    () -> assertEquals(password, result.get().getPassword()),
                    () -> assertEquals(cep, result.get().getRegisteredAddresses().get(0).getCep())
            );
        }

        @ParameterizedTest(name = "#[{index}] Should assertTrue for all entity properties when" +
                " validating by email or cpf with parameters values: Email = {0} | Name = {1} | CPF = {2} |" +
                " Password = {3} | CEP = {4}")
        @CsvFileSource(resources = "/csv/user/UserRepositoryValidData.csv", numLinesToSkip = 1)
        @SqlGroup({
                @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                        scripts = "classpath:scripts/user/BeforeUserRepositoryTest.sql"),
                @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                        scripts = "classpath:scripts/user/AfterUserRepositoryTest.sql")
        })
        @Transactional
        void shouldValidateUserByEmailOrCpf(final String name, final String email, final String cpf) {

            final var result = userRepository.existsByEmailOrCpf(email, cpf);
            assertTrue(result);
        }
    }
}
