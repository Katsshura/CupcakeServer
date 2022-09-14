package com.katsshura.cupcake.core.validation.encrypted;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Target({ TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { EncryptedProvider.class })
public @interface Encrypted {

    /**
     * Name of the fields to be validated and encrypted, must be of the type String
     */
    String[] propertiesName();

    String message() default "Field has an invalid encryption!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
