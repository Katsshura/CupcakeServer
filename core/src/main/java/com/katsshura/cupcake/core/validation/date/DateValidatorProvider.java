package com.katsshura.cupcake.core.validation.date;

import com.katsshura.cupcake.core.exceptions.IncompatibleValueException;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jni.Local;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.Arrays;

public class DateValidatorProvider implements ConstraintValidator<DateValidator, LocalDate> {

    private String[] betweenDates;
    private String regexPattern;

    @Override
    public void initialize(DateValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.betweenDates = constraintAnnotation.between();
        this.regexPattern = constraintAnnotation.regexPattern();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {

        if ((betweenDates.length == 1 && StringUtils.isBlank(betweenDates[0])) || betweenDates.length == 0) {
            return true;
        }

        if (betweenDates.length > 2) {
            throw new RuntimeException("Too many elements provided for between parameter!");
        }

        if (!Arrays.stream(betweenDates).allMatch(s -> s.matches(regexPattern))) {
            throw new IncompatibleValueException("The values provided for the between parameter does not match the expected regex pattern!");
        }

        return betweenDates.length == 1
                ? validateRangeDates(LocalDate.parse(betweenDates[0]), LocalDate.now(), value)
                : validateRangeDates(LocalDate.parse(betweenDates[0]), LocalDate.parse(betweenDates[1]), value);
    }

    private boolean validateRangeDates(final LocalDate startRange,
                                       final LocalDate endRange,
                                       final LocalDate value) {
        var result = value.isAfter(startRange);
        result &= value.isBefore(endRange);
        return result;
    }
}
