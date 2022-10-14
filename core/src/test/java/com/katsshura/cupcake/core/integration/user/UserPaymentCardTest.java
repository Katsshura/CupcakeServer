package com.katsshura.cupcake.core.integration.user;

import com.katsshura.cupcake.core.config.IntegrationTestsConfiguration;
import com.katsshura.cupcake.core.entities.user.UserEntity;
import com.katsshura.cupcake.core.entities.user.payment.UserPaymentCardEntity;
import com.katsshura.cupcake.core.enums.card.CardFlag;
import com.katsshura.cupcake.core.repositories.user.payment.UserPaymentCardRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@IntegrationTestsConfiguration
public class UserPaymentCardTest {

    @Autowired
    private UserPaymentCardRepository userPaymentCardRepository;

    @Nested
    @IntegrationTestsConfiguration
    class CreatePayment {

        @Test
        @SqlGroup({
                @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                        scripts = "classpath:scripts/user/BeforeUserRepositoryTest.sql"),
                @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                        scripts = "classpath:scripts/user/AfterUserRepositoryTest.sql")
        })
        void shouldCreatePaymentWithSuccess() {
            final var user = UserEntity.builder().build();
            user.setId(20L);

            final var userPayment = UserPaymentCardEntity.builder()
                    .cardNumber("5336139285539458")
                    .customName("Test Card")
                    .cvv(424)
                    .expiringDate("01/24")
                    .cardFlag(CardFlag.MASTERCARD)
                    .user(user)
                    .build();

            final var result = userPaymentCardRepository.save(userPayment);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertThat(result.getId(), allOf(greaterThan(0L), notNullValue())),
                    () -> assertEquals(userPayment, result)
            );
        }

        @Test
        void shouldThrowErrorWhenTryingSaveUserInvalidData() {
            final var user = UserEntity.builder().build();
            user.setId(20L);

            final var userPayment = UserPaymentCardEntity.builder()
                    .customName("Test Card")
                    .user(user)
                    .build();

            assertThrows(DataIntegrityViolationException.class,
                    () -> userPaymentCardRepository.save(userPayment));
        }
    }

    @Nested
    @IntegrationTestsConfiguration
    class ListPayment {

        @ParameterizedTest(name = "#[{index}] Should assertNotNull and assertEquals for all entity properties!")
        @CsvSource(value = { "10,4", "20,2", "30,5", "40,2", })
        @SqlGroup({
                @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                        scripts = "classpath:scripts/user/payment/BeforeUserPaymentRepositoryTest.sql"),
                @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                        scripts = "classpath:scripts/user/payment/AfterUserPaymentRepositoryTest.sql")
        })
        @Transactional
        void shouldFindPaymentByUserId(Long userId, Long expectedSize) {
            final var result = userPaymentCardRepository.findAllByUserId(userId);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertFalse(result.isEmpty()),
                    () -> assertEquals(expectedSize, result.size())
            );

        }

    }
}
