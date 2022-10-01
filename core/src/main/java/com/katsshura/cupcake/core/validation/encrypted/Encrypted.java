package com.katsshura.cupcake.core.validation.encrypted;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 * Annotation specific to validate mobile payload encryption and encrypt it with the server encryption rule.
 */
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
