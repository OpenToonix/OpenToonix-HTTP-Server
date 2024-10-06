package com.juansecu.opentoonix.shared.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CryptoUtil {
    public String decrypt(
        final String encryptedText,
        final TextEncryptor textEncryptor
    ) {
        return textEncryptor.decrypt(encryptedText);
    }

    public String encrypt(
        final String text,
        final TextEncryptor textEncryptor
    ) {
        return textEncryptor.encrypt(text);
    }
}
