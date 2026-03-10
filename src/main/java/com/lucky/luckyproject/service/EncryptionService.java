package com.lucky.luckyproject.service;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

    private final StringEncryptor jasyptStringEncryptor;

    // ?ùÏÑ±??Ï£ºÏûÖ (JasyptConfig?êÏÑú ?§Ï†ï??Îπ??¥Î¶Ñ)
    public EncryptionService(StringEncryptor jasyptStringEncryptor) {
        this.jasyptStringEncryptor = jasyptStringEncryptor;
    }

    public String encrypt(String text) {
        return jasyptStringEncryptor.encrypt(text);
    }

    public String decrypt(String encryptedText) {
        // ENC()Î°?Í∞êÏã∏???àÎã§Î©??úÍ±∞ ??Î≥µÌò∏?îÌïòÍ±∞ÎÇò, Jasypt ?§Ï†ï???∞Îùº ?§Î¶Ñ
        return jasyptStringEncryptor.decrypt(encryptedText);
    }
}

