package com.katsshura.cupcake.api.security.service;

import com.katsshura.cupcake.core.services.encrypt.EncryptService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderService implements PasswordEncoder {

    private final static String MATCH_PATTERN = "/^([a-f0-9]{64})$/";

    private final Log logger = LogFactory.getLog(getClass());
    private final EncryptService encryptService;

    public PasswordEncoderService(EncryptService encryptService) {
        this.encryptService = encryptService;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }

        if(rawPassword.toString().matches(MATCH_PATTERN)) return rawPassword.toString();

        return encryptService.encrypt(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }

        if (encodedPassword == null || encodedPassword.length() == 0) {
            logger.warn("Empty encoded password");
            return false;
        }

        return rawPassword.toString().equals(encodedPassword);
    }
}
