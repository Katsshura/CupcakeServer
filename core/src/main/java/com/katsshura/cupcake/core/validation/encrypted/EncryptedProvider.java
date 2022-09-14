package com.katsshura.cupcake.core.validation.encrypted;

import com.katsshura.cupcake.core.services.encrypt.EncryptService;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Service
public class EncryptedProvider implements ConstraintValidator<Encrypted, Object> {

    private final EncryptService encryptService;
    private String[] propertiesName;

    public EncryptedProvider(EncryptService encryptService) {
        this.encryptService = encryptService;
    }

    @Override
    public void initialize(Encrypted constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        propertiesName = constraintAnnotation.propertiesName();
    }

    @Override
    public boolean isValid(final Object value,
                           final ConstraintValidatorContext context) {

        for (String propertyName: propertiesName) {
            final var wrapper = new BeanWrapperImpl(value);
            final var propertyValue = (String) wrapper.getPropertyValue(propertyName);
            //TODO: Validate property value to match encryption from mobile
            final var encryptedValue = this.encryptService.encrypt(propertyValue);
            wrapper.setPropertyValue(propertyName, encryptedValue);
        }

        return true;
    }
}
