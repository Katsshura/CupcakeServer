package com.katsshura.cupcake.core.integration.user;


import com.katsshura.cupcake.core.config.IntegrationTestsConfiguration;
import com.katsshura.cupcake.core.repositories.user.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertAll;

@IntegrationTestsConfiguration
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void contextLoads() {
        assertAll(
                () -> assertThat(this.userRepository, is(notNullValue()))
        );
    }

    @Nested
    @IntegrationTestsConfiguration
    class CreateUser {

        void shouldCreateUserWithSuccess() {

        }

        void shouldThrowErrorWhenTryingSaveUserInvalidData() {

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
