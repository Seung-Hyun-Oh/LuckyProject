package com.lucky.luckyproject.service;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

    private final StringEncryptor jasyptStringEncryptor;

    // 생성자 주입 (JasyptConfig에서 설정한 빈 이름)
    public EncryptionService(StringEncryptor jasyptStringEncryptor) {
        this.jasyptStringEncryptor = jasyptStringEncryptor;
    }

    public String encrypt(String text) {
        return jasyptStringEncryptor.encrypt(text);
    }

    public String decrypt(String encryptedText) {
        // ENC()로 감싸져 있다면 제거 후 복호화하거나, Jasypt 설정에 따라 다름
        return jasyptStringEncryptor.decrypt(encryptedText);
    }
}

