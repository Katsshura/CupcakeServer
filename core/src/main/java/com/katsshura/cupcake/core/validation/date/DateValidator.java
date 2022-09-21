package com.katsshura.cupcake.core.validation.date;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target({ FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {DateValidatorProvider.class})
public @interface DateValidator {

    /**
     * Validates the target date if is contained in the range of dates provided.
     * <p>
     * <br>
     * Expects one or two strings representing a valid date with the pattern defined in the regexPattern field.
     * <p>
     * <br>
     * First String must be the start range and the second one the end range, both not included!
     * <p>
     * <br>
     * If only one string is provided then it will be considered the start of the range, the end of the range will
     * be defined by the LocalDate.now()
     * <p>
     * <br>
     * If the provided dates are not in the expected regex pattern a {@link com.katsshura.cupcake.core.exceptions.IncompatibleValueException} will be thrown.
     * <p>
     * <br>
     * Ex:
     * <p>
     * {"1900-01-01", "2000-01-01"} - Being start range and end range, correspondingly
     * <p>
     * <br>
     * {"1900-01-01"} - Being the start range and the end range to be defined by the current system date on execution
     * <p>
     * <br>
     */
    String[] between() default "";

    /**
     * Verify if the target date pattern match the pattern regex.
     * <p>
     * Default pattern: yyyy-mm-dd
     */
    String regexPattern() default "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";

    String message() default "Field has an invalid encryption!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
