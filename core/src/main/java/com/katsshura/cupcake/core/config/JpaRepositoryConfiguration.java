package com.katsshura.cupcake.core.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.katsshura.cupcake")
@EnableTransactionManagement
@EntityScan("com.katsshura.cupcake.core.entities")
public @interface JpaRepositoryConfiguration {
}
