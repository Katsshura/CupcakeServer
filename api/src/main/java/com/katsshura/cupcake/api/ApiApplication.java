package com.katsshura.cupcake.api;

import com.katsshura.cupcake.core.config.JpaRepositoryConfiguration;
import com.katsshura.cupcake.core.config.PropertiesSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = {"com.katsshura.cupcake.api", "com.katsshura.cupcake.core"})
@Import(JpaRepositoryConfiguration.class)
@PropertiesSource
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
