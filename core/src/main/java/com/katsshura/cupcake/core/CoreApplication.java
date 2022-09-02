package com.katsshura.cupcake.core;

import com.katsshura.cupcake.core.config.JpaRepositoryConfiguration;
import com.katsshura.cupcake.core.config.PropertiesSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = {"com.katsshura.cupcake.core"})
@Import(JpaRepositoryConfiguration.class)
@PropertiesSource
public class CoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }
}
