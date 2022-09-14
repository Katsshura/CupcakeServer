package com.katsshura.cupcake.core.services.encrypt;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public final class EncryptService {
    
    private final MessageDigest digest;

    public EncryptService() throws NoSuchAlgorithmException {
        digest = MessageDigest.getInstance("SHA-256");
    }

    public String encrypt(final String target) {
        final var hash = this.createSHA(target);
        final var encryptedStr = new StringBuilder(
                new BigInteger(1, hash).toString(16)
        );

        while (encryptedStr.length() < 32) {
            encryptedStr.insert(0, '0');
        }

        return encryptedStr.toString();
    }

    private byte[] createSHA(String target) {
        return digest.digest(target.getBytes(StandardCharsets.UTF_8));
    }
}
